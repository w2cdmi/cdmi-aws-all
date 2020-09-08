console.log('here we go');
new Promise ( resolve =>{
  setTimeout( () => {
  resolve('hello');
},2000);
})
  .then(value => {
    console.log( value);
    return new Promise( resolve => {
      setTimeout(() => {
        console.log(' world');
      },2000);
    });
  })
  .then( value => {
    console.log( value  +  ' world');
  })
