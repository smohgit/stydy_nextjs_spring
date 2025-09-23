'use client'

import {useParams} from "next/navigation";
import {useEffect, useState} from "react";
import Link from "next/link";

export default function ArticleDetail() {
  const params = useParams();
  const [article, setArticle] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:8090/api/v1/articles/${params.id}`, )
      .then(result => result.json())
      .then(result => setArticle(result.data.articles))
  }, [])

  // const params = useParams();
  // console.log('[sm5 debug][default.ArticleDetail]  : ', params );



  return (
    <>
      <div>게시판 상세 {params.id}번</div>
      <div>{article.createdDate}</div>
      <div>{article.modifiedDate}</div>
      <div>{article.subject}</div>
      <div>{article.content}</div>
      <Link href={`/article/${params.id}/edit`}>수정</Link>
    </>
  );
}