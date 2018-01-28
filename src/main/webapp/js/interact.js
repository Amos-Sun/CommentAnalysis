axios.defaults.baseURL = 'http://localhost:8080/analysis/';
// axios.defaults.headers.post['Content-Type'] = 'application/json';
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
    return '<div class="col-md-4 resent-grid recommended-grid slider-top-grids">'
        + '<div class="resent-grid-img recommended-grid-img">'
        + '<a href="' + videos[index][0] + '" target="_blank"> <img src="' + videos[index][5] + '" alt=""/></a> '
        + '<div class="clck"><span class="glyphicon glyphicon-time" aria-hidden="true"></span></div>'
        + '</div>'
        + '<div class="resent-grid-info recommended-grid-info">'
        + '<h3><a href="' + videos[index][0] + '" target="_blank" '
        + 'class="title title-info">' + videos[index][1] + '</a></h3>'
        + '<ul>'
        + '<li><p class="author author-info"><a href="#" class="author">' + videos[index][4] + '</a></p></li>'
        + '<li class="right-list"><p class="views views-info">' + videos[index][3] + '</p></li>'
        + '</ul>'
        + '</div>'
        + '</div>';
}
function addToMainContent(videos) {
    var contentDiv = document.getElementById('content');
    var html = '';
    for (var i = 0; i < videos.length; i++) {
        html += setDiv(videos, i);
    }
    html += '<div class="clearfix"></div>';
    contentDiv.innerHTML = html;
}

var pageBar = new Vue({
    el: '.page-bar',
    data: {
        all: 8, //总页数
        cur: 1//当前页码
    },
    watch: {
        cur: function (oldValue, newValue) {
            console.log(arguments);
        }
    },
    methods: {
        btnClick: function (data) {//页码点击事件
            if (data != this.cur) {
                this.cur = data
            }
        },
        pageClick: function () {
            console.log('现在在' + this.cur + '页');
        }
    },

    computed: {
        indexs: function () {
            var left = 1;
            var right = this.all;
            var ar = [];
            if (this.all >= 5) {
                if (this.cur > 3 && this.cur < this.all - 2) {
                    left = this.cur - 2
                    right = this.cur + 2
                } else {
                    if (this.cur <= 3) {
                        left = 1
                        right = 5
                    } else {
                        right = this.all
                        left = this.all - 4
                    }
                }
            }
            while (left <= right) {
                ar.push(left)
                left++
            }
            return ar
        }

    }
})

