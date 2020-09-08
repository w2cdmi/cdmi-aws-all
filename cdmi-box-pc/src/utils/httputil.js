/**
 * Created by quxiangqian on 2017/10/22.
 */
import reqwest from 'reqwest'
import Cookies from 'universal-cookie'
import { AppConfig } from 'config'
import { getUserInfo } from './UserInfoUtil'

class HttpUtil {
  constructor () {
    this.url = ''
    this.method = 'post'
    this.data = {}
    this.type = 'json'
  }

  getHeader =() => {
    let h = { 'Content-Type': 'application/json' }
    let cookies = new Cookies()
    let token = cookies.get('token')
    if (token) {
      h.Authorization = ''
    }
    return h
  }
  setPram= (f, err) => {
    if (this.mode === 'ecm') {
      this.baseAddr = AppConfig.ApiEcmBaseUrl
    } else {
      this.baseAddr = AppConfig.ApiUfmBaseUrl
    }
    reqwest({
      url: this.baseAddr + this.url,
      method: this.method,
      data: this.data,
      type: this.type,
      headers: {
        'Content-Type': 'application/json',
        Authorization: getUserInfo().token,
      },
      error: err,
      success: f,
    })
  }
  error = (err) => {
    console.log(err)
  }
  post = (success, error) => {
    this.method = 'post'
    this.setPram(success, error)
  }
  get = (success, error) => {
    this.method = 'get'
    this.setPram(success, error)
  }
  delete = (success, error) => {
    this.method = 'delete'
    this.setPram(success, error)
  }
  put = (success, error) => {
    this.method = 'put'
    this.setPram(success, error)
  }
}
export default HttpUtil
