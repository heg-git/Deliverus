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

  const getLogInResult = async (data) => {
    const url = "http://localhost:8080/login";
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include",
      body: JSON.stringify(data),
    });
    return response.text();
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const data = { userid: username, passwd: password };
    try {
      const result = await getLogInResult(data);
      if (result === "success") {
        console.log("Log In Success");
        handleLogIn();
      } else {
        console.log("Log In failed from server");
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
        <button>로그인</button>
      </form>
      <Link to="/register">회원가입</Link>
    </>
  );
};

export default LogIn;
