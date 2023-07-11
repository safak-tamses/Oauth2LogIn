import React, { useState } from 'react';
import axios from 'axios';
import {
  MDBBtn,
  MDBContainer,
  MDBRow,
  MDBCol,
  MDBCard,
  MDBCardBody,
  MDBInput,
  MDBIcon,
  MDBCheckbox
} from 'mdb-react-ui-kit';

const URL = "http://localhost:8080/login";
const URLg = "http://localhost:8080/oauth"

function App() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  function handleSubmit(event) {
    event.preventDefault();
    console.log(username)
    console.log(password)

    const data = {
      email: username,
      password: password
    };

    const config = {
      headers: {
        'Content-Type': 'application/json'
      }
    };

    axios.post(URL, data, config)
      .then(response => {
        console.log(response.data)
        alert(response.data.message)
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('role', response.data.roleToken);
        console.log("giriş yapıldı")
        console.log("token: " + localStorage.getItem("token"))
        console.log("role: " + localStorage.getItem("role"))
      })
      .catch(error => {

      });
  }
  const oauthLog = () =>{
    try{
      axios.get(URLg)
      .then(response => {
        console.log(response.data)
      })
      .catch(error => {
        console.log("noluyo")
      });
    } 
    catch{
      console.log("olmuyor ")
    }

  }

  return (
    <MDBContainer fluid>
      <MDBRow className='d-flex flex-column justify-content-center align-items-center h-100'>
        <MDBCol col='12'>
          <MDBCard className='bg-white my-5 mx-auto' style={{ borderRadius: '1rem', maxWidth: '500px' }}>
            <MDBCardBody className='p-5 w-100 d-flex flex-column'>
              <h2 className="fw-bold mb-4 text-center">Sign in</h2>
              <p className="text-white-50 mb-3">Please enter your login and password!</p>

              <form onSubmit={handleSubmit}>
                <MDBInput wrapperClass='mb-4 w-100' label='Email address' value={username} onChange={e => setUsername(e.target.value)} type='email' size="lg" />
                <MDBInput wrapperClass='mb-4 w-100' label='Password' value={password} onChange={e => setPassword(e.target.value)} type='password' size="lg" />
                <hr className="my-4" />
                <MDBCheckbox name='flexCheck' id='flexCheckDefault' className='' label='Remember password' />

                <MDBBtn className="mt-4 w-100" size="lg" type='submit'>
                  Login
                </MDBBtn>
              </form>
              <hr className="my-4" />

              <MDBBtn className="mb-4 w-100" size="lg" style={{ backgroundColor: '#dd4b39' }} onClick={oauthLog}>
                <MDBIcon fab icon="google" className="mx-2" />
                Sign in with google
              </MDBBtn>


            </MDBCardBody>
          </MDBCard>
        </MDBCol>
      </MDBRow>
    </MDBContainer>
  );
}

export default App;
