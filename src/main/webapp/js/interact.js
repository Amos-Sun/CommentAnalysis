axios.defaults.baseURL = 'http://localhost:8080/demo/';
axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

function getVideos() {
    axios.get('/video/get-all?pageNum=1&pageSize=3')
        .then(function (response) {
            alert(response)
            console.log(response);
        })
        .catch(function (response) {
            alert(response)
            console.log(response);
        });
}