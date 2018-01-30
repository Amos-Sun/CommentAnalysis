axios.defaults.baseURL = 'http://localhost:8080/analysis/';
axios.defaults.headers.post['Content-Type'] = 'text/plain';

function getVideos() {
    var videos = new Array();
    axios.get('/video/get-all?pageNum=1&pageSize=20')
        .then(function (response) {
            var len = response.data.data.length;
            for (var i = 0; i < len; i++) {
                videos = getVideoDetail(videos, response.data.data[i], i);
            }
            addToMainContent(videos);
            console.log(videos);
        })
        .catch(function (response) {
            alert(response);
            console.log(response);
        });
}

function getVideoDetail(videos, data, index) {
    videos[index] = new Array();
    videos[index][0] = data.videoUrl;
    videos[index][1] = data.name;
    videos[index][2] = data.detail;
    videos[index][3] = data.videoType;
    videos[index][4] = data.actors;
    videos[index][5] = data.picUrl;
    return videos;
}

function setDiv(videos, index) {
    return '<div class="col-md-3 resent-grid recommended-grid">'
        + '<div class="resent-grid-img recommended-grid-img">'
        + '<a href="' + videos[index][0] + '" target="_blank"> '
        + '<img style="height: 300px;" src="' + videos[index][5] + '" alt=""/>'
        + '</a> '
        + '<div class="resent-grid-info recommended-grid-info video-info-grid">'
        + '<h3><a href="' + videos[index][0] + '" target="_blank" '
        + 'class="title title-info">' + videos[index][1] + '</a></h3>'
        + '<p class="author author-info">' + videos[index][4] + '</p><br/>'
        + '<p class="views views-info">' + videos[index][3] + '</p>'
        + '</div>'
        + '</div>'
        + '</div>';
}

function addToMainContent(videos) {
    var contentDiv = document.getElementById('content');
    var allDiv = '';
    var html = '';
    for (var i = 0; i < videos.length; i++) {
        html += setDiv(videos, i);
        if ((i + 1) % 4 == 0) {
            html += '<div class="clearfix"></div>';
            html = '<div class="recommended-grids">' + html + '</div>';
            allDiv += html;
            html = '';
        }
    }
    contentDiv.innerHTML = allDiv;
}

