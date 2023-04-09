import { useState } from "react";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [nickname, setNickname] = useState("");

  const handleIdInput = (e) => {
    setUsername(e.target.value);
  };

  const handlePwInput = (e) => {
    setPassword(e.target.value);
  };

  const handleNicknameInput = (e) => {
    setNickname(e.target.value);
  };

  /** 정규표현식을 사용해 id, pw, nickname의 유효성 검사를 진행하는 함수 */
  const validateForm = () => {
    const idPwRegex = /^[a-zA-Z0-9]{4,}$/;
    const nicknameRegex = /^[a-zA-Z0-9가-힣]{4,}$/;
    if (
      idPwRegex.test(username) &&
      idPwRegex.test(password) &&
      nicknameRegex.test(nickname)
    )
      return true;
    return false;
  };

  /** /member/register 에 회원가입 요청을 보내고 응답을 반환하는 함수 */
  const getRegistrationResult = async (data) => {
    const url = "http://localhost:8080/member/register";
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include",
      body: JSON.stringify(data),
    });

    return response.json();
  };

  /**
   * 1. 제출하려는 폼의 유효성 검사 진행
   * 2. 서버로부터 회원가입 성공 유무 문자열 받는 함수 호출
   * 3. 성공 유무에 따라 홈페이지로 리다이렉트
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) {
      return false;
    }

    const data = { userid: username, nickname: nickname, passwd: password };

    try {
      const result = await getRegistrationResult(data);
      if (result.nickname) {
        console.log("Registration Success");
        navigate("/");
      } else {
        console.log("Registration Fail");
      }
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <>
      <form onSubmit={handleSubmit} method="post">
        <label htmlFor="id">ID: </label>
        <input onChange={handleIdInput} id="id" type="text" value={username} />
        <br />
        <label htmlFor="password">PW: </label>
        <input
          onChange={handlePwInput}
          id="password"
          type="password"
          value={password}
        />
        <br />
        <label htmlFor="nickname">Nickname: </label>
        <input
          onChange={handleNicknameInput}
          id="nickname"
          type="text"
          value={nickname}
        />
        <br />
        <button>회원가입</button>
      </form>
    </>
  );
};

export default Register;
