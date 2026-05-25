import { useState } from "react";
import "./App.css";

function App() {
  const [page, setPage] = useState("login");
  const [currentUser, setCurrentUser] = useState(null);

  return (
    <>
      {page === "login" && (
        <Login setPage={setPage} setCurrentUser={setCurrentUser} />
      )}
      {page === "register" && <Register setPage={setPage} />}
      {page === "dashboard" && (
        <Dashboard user={currentUser} setPage={setPage} />
      )}
    </>
  );
}

/* ---------------- LOGIN ---------------- */
function Login({ setPage, setCurrentUser }) {
  const [phone, setPhone] = useState("");
  const [pin, setPin] = useState("");

  const handleLogin = () => {
    // Validation
    if (phone.length !== 10 || isNaN(phone)) {
      alert("Phone number must be 10 digits");
      return;
    }

    if (pin.length !== 4 || isNaN(pin)) {
      alert("PIN must be 4 digits");
      return;
    }

    const user = JSON.parse(localStorage.getItem(phone));

    if (!user) {
      alert("User not found! Please register.");
      return;
    }

    if (user.pin === pin) {
      setCurrentUser(user);
      setPage("dashboard");
    } else {
      alert("Incorrect PIN ❌");
    }
  };

  return (
    <div className="container">
      <div className="card">
        <h1 className="title">MRUPay</h1>
        <p className="subtitle">Malla Reddy University</p>

        <input
          type="text"
          placeholder="Enter your phone number"
          value={phone}
          onChange={(e) => setPhone(e.target.value)}
        />

        <input
          type="password"
          placeholder="Enter your 4-digit PIN"
          value={pin}
          onChange={(e) => setPin(e.target.value)}
        />

        <button onClick={handleLogin}>Login</button>

        <p className="register" onClick={() => setPage("register")}>
          Register here
        </p>
      </div>
    </div>
  );
}

/* ---------------- REGISTER ---------------- */
function Register({ setPage }) {
  const [name, setName] = useState("");
  const [phone, setPhone] = useState("");
  const [pin, setPin] = useState("");

  const handleRegister = () => {
    if (!name || !phone || !pin) {
      alert("Fill all fields");
      return;
    }

    if (phone.length !== 10 || isNaN(phone)) {
      alert("Phone must be 10 digits");
      return;
    }

    if (pin.length !== 4 || isNaN(pin)) {
      alert("PIN must be 4 digits");
      return;
    }

    const userData = { name, phone, pin };
    localStorage.setItem(phone, JSON.stringify(userData));

    alert("Registered Successfully ✅");
    setPage("login");
  };

  return (
    <div className="container">
      <div className="card">
        <h1 className="title">PhonePe</h1>
        <p className="subtitle">Create Account</p>

        <input
          type="text"
          placeholder="Full Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <input
          type="text"
          placeholder="Phone Number"
          value={phone}
          onChange={(e) => setPhone(e.target.value)}
        />

        <input
          type="password"
          placeholder="Create 4-digit PIN"
          value={pin}
          onChange={(e) => setPin(e.target.value)}
        />

        <button onClick={handleRegister}>Register</button>

        <p className="register" onClick={() => setPage("login")}>
          Back to Login
        </p>
      </div>
    </div>
  );
}

/* ---------------- DASHBOARD ---------------- */
function Dashboard({ user, setPage }) {
  const [balance, setBalance] = useState(0);
  const [upi, setUpi] = useState("");
  const [amount, setAmount] = useState("");

  const addMoney = () => {
    const amt = Number(prompt("Enter amount to add"));
    if (!amt || amt <= 0) return;
    setBalance(balance + amt);
  };

  const sendMoney = () => {
    if (!upi || !amount) {
      alert("Fill all fields");
      return;
    }
    if (Number(amount) > balance) {
      alert("Insufficient Balance");
      return;
    }
    setBalance(balance - Number(amount));
    alert("Money Sent Successfully ✅");
    setUpi("");
    setAmount("");
  };

  const handleLogout = () => {
    setPage("login");
  };

  return (
    <div className="container">
      <div className="card" style={{ width: "450px" }}>
        <div style={{ display: "flex", justifyContent: "space-between" }}>
          <h3>PhonePe</h3>
          <button onClick={handleLogout} style={{ width: "80px", padding: "6px" }}>
            Logout
          </button>
        </div>

        <h2>Welcome, {user.name}</h2>

        {/* Wallet */}
        <div style={{ marginTop: "20px", padding: "20px", background: "#f5f7ff", borderRadius: "10px" }}>
          <h3>Wallet Balance</h3>
          <h1>₹{balance.toFixed(2)}</h1>
          <button onClick={addMoney}>Add Money</button>
        </div>

        {/* Send Money */}
        <div style={{ marginTop: "20px", textAlign: "left" }}>
          <h3>Send Money</h3>

          <input
            type="text"
            placeholder="Recipient UPI ID"
            value={upi}
            onChange={(e) => setUpi(e.target.value)}
          />

          <input
            type="number"
            placeholder="Amount (₹)"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />

          <button onClick={sendMoney}>Send</button>
        </div>
      </div>
    </div>
  );
}
export default App;