package com.example.niukecommunity.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建一个基于前缀树的过滤器
 */

@Component
public class SensitiveFilter {

    private static final Logger logger= LoggerFactory.getLogger(SensitiveFilter.class);
    //替换符
    private static final String REPLACEMENT="***";
    private TrieNode rootNode=new TrieNode();

    @PostConstruct
    public void init(){
        try(
                //读取配置文件中的敏感词
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                ){
                String keyword;
                while((keyword=reader.readLine())!=null){
                    //添加到前缀树
                    this.addKeyWord(keyword);
                }
        }catch (IOException e){
            logger.error("加载敏感词文件失败"+e.getMessage());
        }
    }

    //将一个敏感词添加到前缀树中
     private void addKeyWord(String keyword){
        TrieNode tempNode=rootNode;
        for(int i=0;i<keyword.length();i++){
            char c=keyword.charAt(i);
            TrieNode subNode=tempNode.getSubNode(c);
            if(subNode==null){
                subNode=new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            //指向子节点，进行下一轮循环
            tempNode=subNode;
            //设置结束标记
            if(i==keyword.length()-1){
                tempNode.setKeyWordEnd(true);
            }
        }
     }

    /**
     * 过滤敏感词
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
     public String filter(String text){
         if(StringUtils.isBlank(text)){
             return null;
         }
         //指针1
         TrieNode tempNode=rootNode;
         int begin=0;
         int position=0;
         StringBuilder sb=new StringBuilder();
         while(position<text.length()){
             char c=text.charAt(position);
             if(isSymbol(c)){
                 //若指针1处于根节点，将此节点计入结果，让指针2向下走一步
                 if(tempNode==rootNode){
                     sb.append(c);
                     begin++;
                 }
                 position++;
                 continue;
             }
             //检查下节点
             tempNode=tempNode.getSubNode(c);
             if(tempNode==null){
                //以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                position=++begin;
                //重新指向根节点
                 tempNode=rootNode;
             }
             else if(tempNode.isKeyWordEnd()){
                 //发现敏感词，替换字符串
                 sb.append(REPLACEMENT);
                 //进入下一个位置
                 begin=++position;
                 //重新指向根节点
                 tempNode=rootNode;
             }
             else{
                 //继续检查下一个字符
                 if(position<text.length()-1){
                     position++;
                 }
             }
         }
         return sb.toString();

     }

     //判断是否为符号
    private boolean isSymbol(Character c){
         return !CharUtils.isAsciiAlphanumeric(c) && (c<0x2E80 || c>0x9FFF);
    }

    //前缀树
    private class TrieNode{
        //关键词结束标识
        private boolean isKeyWordEnd=false;

        private Map<Character,TrieNode> subNodes=new HashMap<>();

        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }
        //添加子节点
        public void addSubNode(Character c,TrieNode node){
            subNodes.put(c,node);
        }
        //获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }

}
