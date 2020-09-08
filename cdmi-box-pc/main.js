const pkg = require('./package.json')
const electron = require('electron')
// Module to control application life.
const app = electron.app
// Module to create native browser window.
const BrowserWindow = electron.BrowserWindow

const path = require('path')
const url = require('url')

const ipc = require('electron').ipcMain

// Keep a global reference of the window object, if you don't, the window will
// be closed automatically when the JavaScript object is garbage collected.
let mainWindow

global.sharedObject = {
  userInfo: {
    token: 'StorBox/2F4A88345A3BA2A66A257E365CCC45D43F6D3A7DA86BA70AA762910D',
    cloudUserId: 237,
  },
}
function createWindow () {
  // Create the browser window.
  mainWindow = new BrowserWindow({ width: 800, height: 600, webPreferences: { webSecurity: false} })
  if (pkg.DEV) {
    mainWindow.loadURL('http://localhost:8000/')
    // mainWindow.loadURL(url.format({
    //   pathname: path.join(__dirname, 'auth.html'),
    //   protocol: 'file:',
    //   slashes: true,
    // }))
  } else {
    mainWindow.loadURL(url.format({
      pathname: path.join(__dirname, './dist/index.html'),
      protocol: 'file:',
      slashes: true,
    }))
  }

  // and load the index.html of the app.
  // mainWindow.loadURL(url.format({
  //   pathname: path.join(__dirname, 'index.html'),
  //   protocol: 'file:',
  //   slashes: true,
  // }))

  // Open the DevTools.
  // mainWindow.webContents.openDevTools()

  // Emitted when the window is closed.
  mainWindow.on('closed', () => {
    // Dereference the window object, usually you would store windows
    // in an array if your app supports multi windows, this is the time
    // when you shoul
    // d delete the corresponding element.
    mainWindow = null
  })

  ipc.on('openNewWindow', () => {
    mainWindow.loadURL('http://localhost:8000/')
  })
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', createWindow)

// Quit when all windows are closed.
app.on('window-all-closed', () => {
  // On OS X it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', () => {
  // On OS X it's common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (mainWindow === null) {
    createWindow()
  }
})
// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and require them here.
