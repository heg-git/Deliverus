import { useState } from "react";

const Register = () => {
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

  const handleSubmit = async (e) => {
    e.preventDefault();
    const url = "http:localhost:8080/member/register";
    const data = { username: username, nickname: nickname, password: password };
    const response = await fetch(url, {
      method: "POST",
      credentials: "include",
      body: JSON.stringify(data),
    });
  };

  const postRegisteration = () => {};

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
