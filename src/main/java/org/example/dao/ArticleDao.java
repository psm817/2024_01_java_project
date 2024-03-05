package org.example.dao;

import org.example.dto.Article;
import org.example.dto.Member;

import java.util.ArrayList;
import java.util.List;

public class ArticleDao extends Dao {
    public List<Article> articles;

    public ArticleDao() {
        articles = new ArrayList<Article>();
    }

    public void add(Article article) {
        articles.add(article);
        lastId++;
    }
}
