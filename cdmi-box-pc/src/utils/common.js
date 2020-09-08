import moment from 'moment'

const arrUnit = ['B', 'KB', 'MB', 'GB', 'TB', 'PB']
const formatFileSize = (fileSize) => {
  if(fileSize == 0){
    return "0B"
  }else if(fileSize == -1){
    return "无限制"
  }else{
    let powerIndex = Math.log2(fileSize) / 10
    powerIndex = Math.floor(powerIndex)
    let len = arrUnit.length
    powerIndex = powerIndex < len ? powerIndex : len - 1
    let sizeFormatted = fileSize / Math.pow(2, powerIndex * 10)
    sizeFormatted = sizeFormatted.toFixed(2)
    return `${sizeFormatted} ${arrUnit[powerIndex]}`
  }
  
}

const formatDate = (timestamp) => {
  return moment(timestamp).format('YYYY-MM-DD HH:mm:ss')
}

const randomCode = (L) => {
  let s = ''
  let randomchar = function () {
    let n = Math.floor(Math.random() * 62)
    if (n < 10) return n // 1-10
    if (n < 36) return String.fromCharCode(n + 55) // A-Z
    return String.fromCharCode(n + 61) // a-z
  }
  while (s.length < L) s += randomchar()
  return s
}

const validateEmail = (value) => {
  let reg = new RegExp('^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$')
  return reg.test(value)
}

export { formatFileSize, formatDate, randomCode, validateEmail }
