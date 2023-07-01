import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';

const Home = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    let handleSubmit = async (e) => {
        e.preventDefault();
        try {
          let res = await fetch("http://localhost:8080/api/v1/auth/signin", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
              email: email,
              password: password,
            }),
          });
          let resJson = await res.json();
          if (res.status === 200) {
            console.log('token: ' + resJson['token']);
            localStorage.setItem('token', resJson['token']);
            navigate('/merchants');
          } else {
            console.log("Some error occured");
          }
        } catch (err) {
          console.log(err);
        }
      };

    return(
            <div>
                <h1>Home</h1>

                <form onSubmit={handleSubmit}>
                    <div className='form-group'>
                        <label for="email">Email:</label>
                        <input class="form-control" value={email} type="email" placeholder="john@beatles.com" id="email" name="email" onChange={(e) => setEmail(e.target.value)}/>
                    </div>
                    <div className='form-group'>
                        <label for="password">Password:</label>
                        <input class="form-control" value={password} type="password" id="password" name="password" onChange={(e) => setPassword(e.target.value)}/>
                    </div>
                    <button type="submit" class="btn btn-primary">Log in</button>
                </form>
            </div>
        );
}
export default Home;