import { useState } from "react";
import { Link, Routes, Route } from "react-router-dom";
import Main from "./components/Main";
import "./App.css";
import Register from "./components/Register";
import NotFound from "./components/NotFound";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLogOutClicked = () => {
    setIsLoggedIn(false);
  };

  /** 로그인 여부를 부모 컴포넌트에서만 관리하도록 하는 함수 */
  const handleLogIn = () => {
    setIsLoggedIn(true);
  };

  return (
    <>
      <header>
        <Link to="/">
          <button>Main</button>
        </Link>
        {isLoggedIn && <button onClick={handleLogOutClicked}>Log Out</button>}
      </header>
      <main>
        <Routes>
          <Route
            path="/"
            element={<Main isLoggedIn={isLoggedIn} handleLogIn={handleLogIn} />}
          />
          <Route path="/register" element={<Register />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </main>
    </>
  );
}

export default App;
