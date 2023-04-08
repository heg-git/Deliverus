import { Link } from "react-router-dom";
import { useState } from "react";

const LogIn = ({ handleLogIn }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleIdInput = (e) => {
    setUsername(e.target.value);
  };

  const handlePwInput = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // 이곳에서 로그인을 합니다.
    if (username === "" && password === "") {
      handleLogIn();
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
        <button>로그인</button>
      </form>
      <Link to="/register">회원가입</Link>
    </>
  );
};

export default LogIn;
