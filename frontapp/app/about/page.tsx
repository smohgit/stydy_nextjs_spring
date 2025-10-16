'use client'

import {useEffect, useState} from "react";
import api from "@/app/utils/api";

const About = () => {
	const [member, setMember] = useState(false);
	
	useEffect(() => {
		api.get("/members/me")
			.then(response => setMember(response.data.data.memberDto))
	}, [])
	
	return (
		<>
			<h1>소개 페이지</h1>
			<ul>
				<li>id : {member.id}</li>
				<li>username : {member.username}</li>
			</ul>
		</>
	);
};

export default About;

