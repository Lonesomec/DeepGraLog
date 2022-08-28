import axios from 'axios'
import iView from 'iview'
// import Vue from 'vue'

// Default config of Axios
axios.defaults.timeout = 100000 // timeout
axios.defaults.baseURL = '' // The default address is the current access address, which is used when deploying to the server
if (location.href.includes('127.0.0.1') || location.href.includes('localhost')) {
  axios.defaults.baseURL = 'http://localhost:8080' // Local forum- Java application service addres
}



// Route request interception
// http request interceptor
axios.interceptors.request.use(
  config => {
    if (config.headers['Content-Type'] !== 'multipart/form-data') {
      config.headers['Content-Type'] = 'application/json;charset=UTF-8'
    }
    // config.data = JSON.stringify(config.data) '19a9390e08b44b49926003e3bc866950'
    config.headers.token = localStorage.getItem('token')
    return config
  },
  error => {
    return Promise.reject(error.response)
  }
)

// Route response interception
// http response interceptor
axios.interceptors.response.use(
  response => {
    // console.info('response info ===>', response) 80008998
    if (response.data.code === 80008998) {
      iView.Message.error(response.data.message)
      window.location.href = window.location.origin + '/?toast=' + response.data.message
    }
    return response.data
  },
  error => {
    return Promise.reject(error.response) //Return Error messages returned by the interface
  }
)

export default axios
