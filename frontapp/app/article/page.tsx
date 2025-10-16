'use client'

import React, {useEffect, useState} from 'react';
import Link from "next/link";
import api from "@/app/utils/api";



const Article = () => {
  const [articles, setArticles] = useState([]);

  useEffect(() => {
    fetchArticles()
  }, [])

  const fetchArticles = () => {
    api.get("/articles")
      .then(
        response => setArticles(response.data.data.articles)
      )
      .catch (err => {
        console.log(err)
      })
  }

  const handleDelete = async (id) => {
    await api.delete(`/articles/${id}`)
      .then(() => fetchArticles())
  };
  return (
    <>
      <ArticleForm fetchArticles={fetchArticles}/>

      <h4>번호 / 제목 / 생성일</h4>
      {articles.length == 0 ? (
        <p>현재 게시물이 없습니다.</p>
      ) : (
        <ul>
          {
            articles.map((article) =>
              <li key={article.id}>
                {article.id} / <Link href={`/article/${article.id}`}>{article.subject}</Link> / {article.createdDate}
                <button onClick={() => handleDelete(article.id)}> 삭제 </button>
              </li>
            )}
        </ul>
      )
      }
    </>
  );
};


function ArticleForm({fetchArticles}) {
  const initArticle = {subject: '', content: ''}
  const [article, setArticle] = useState(initArticle);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setArticle({...article, [name]: value});
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    await api.post("/articles", article)
      .then(function (response) {
        fetchArticles();
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
    <>
      <h4>게시물 작성</h4>
      <form onSubmit={handleSubmit}>
        <label htmlFor="title">
          제목 :
          <input type="text" name="subject" value={article.subject} onChange={handleChange}/>
        </label>
        <br/>
        <label htmlFor="subject">
          내용 :
          <input type="text" name="content" value={article.content} onChange={handleChange}/>
        </label>

        <input type={"submit"} value={"등록"}/>
      </form>

    </>
  );
}


export default Article;