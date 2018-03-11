axios.defaults.baseURL = 'http://localhost:8080/analysis/';
axios.defaults.headers.post['Content-Type'] = 'text/plain';

var totalRecord;
var currentPageStatus; // 0 全部电影，1 根据男生评价排序  2 根据女生评价排序
function getVideos(pageNum, pageSize) {
    axios.get('/video/get-all', {
        params: {
            pageNum: pageNum,
            pageSize: pageSize
        }
    })
        .then(function (response) {
            var len = response.data.data.length;
            totalRecord = response.data.other.total;
            goPage(pageNum, pageSize);
            for (var i = 0; i < len; i++) {
                videos = getVideoDetail(videos, response.data.data[i], i);
            }
            addToMainContent(videos);
            currentPageStatus = 0;
            console.log(videos);
        })
        .catch(function (response) {
            alert(response);
            console.log(response);
        });
    var videos = new Array();
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

var pageSize = 20;//每页显示行数
var currentPage_ = 1;//当前页全局变量，用于跳转时判断是否在相同页，在就不跳，否则跳转。
var totalPage;//总页数

/**
 * 分页函数
 * pno--页数
 * psize--每页显示记录数
 * 分页部分是从真实数据行开始，因而存在加减某个常数，以确定真正的记录数
 * 纯js分页实质是数据行全部加载，通过是否显示属性完成分页功能
 **/
function goPage(pno, psize) {
    var num = totalRecord;//表格所有行数(所有记录数)

    pageSize = psize;//每页显示行数
    //总共分几页
    if (num / pageSize > parseInt(num / pageSize)) {
        totalPage = parseInt(num / pageSize) + 1;
    } else {
        totalPage = parseInt(num / pageSize);
    }
    var currentPage = pno;//当前页数
    currentPage_ = currentPage;

    var tempStr = "共" + totalPage + "页 当前第" + currentPage + "页";
    var tempOption = "";
    for (var i = 1; i <= totalPage; i++) {
        tempOption += '<option value=' + i + '>' + i + '</option>'
    }
    document.getElementById("barcon1").innerHTML = tempStr;

    if (currentPage > 1) {
        $("#firstPage").on("click", function () {
            getVideos(1, psize);
        }).removeClass("ban");
        $("#prePage").on("click", function () {
            getVideos(currentPage - 1, psize);
        }).removeClass("ban");
    } else {
        $("#firstPage").off("click").addClass("ban");
        $("#prePage").off("click").addClass("ban");
    }

    if (currentPage < totalPage) {
        $("#nextPage").on("click", function () {
            getVideos(currentPage + 1, psize);
        }).removeClass("ban");
        $("#lastPage").on("click", function () {
            getVideos(totalPage, psize);
        }).removeClass("ban")
    } else {
        $("#nextPage").off("click").addClass("ban");
        $("#lastPage").off("click").addClass("ban");
    }

    $("#jumpWhere").html(tempOption).val(currentPage);
}

function jumpPage() {
    var num = parseInt($("#jumpWhere").val());
    if (num != currentPage_) {
        if (currentPageStatus == 0) {
            getVideos(num, pageSize);
        }
    }
}

$(function () {
    getVideos(currentPage_, pageSize);
});


