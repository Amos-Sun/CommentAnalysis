axios.defaults.baseURL = 'http://localhost:8080/analysis/';
axios.defaults.headers.post['Content-Type'] = 'text/plain';

$(function () {
    var cid = document.URL.split("=")[1];
    getVideoDetail(cid);
});

function getVideoDetail(cid) {
    axios.get('/video/get-bycid', {
        params: {
            cid: cid
        }
    }).then(function (response) {
        console.log(response.data);
        initChart(response.data.data);
        showVideoInfo(response.data.data);
    }).catch(function (response) {
        alert(response);
    });
}

//电影信息显示
function showVideoInfo(data) {
    var info = document.getElementById('video');
    var html = '';
    html = html + setDiv(data);
    info.innerHTML = html;

}

function setDiv(video) {
    return '<div class="videoPic">'
        + '<a href= "' + video.videoUrl + '" target="_blank" > '
        + '<img style="height: 300px;" src="' + video.picUrl + '" alt=""/>'
        + '</a>'
        + '</div>'
        + '<div class="videoInfo">'
        + '<p >《' + video.name + '》简介</p><br/>'
        + '<p >演员：' + video.actors + '</p>'
        + '<p >' + video.detail + '</p>'
        + '</div>';
}


//绘制图形
function initChart(video) {
    var chartAll = echarts.init(document.getElementById('chart-all'));
    var chartSex = echarts.init(document.getElementById('chart-sex'));
    var title = video.name;
    var subStr = "情感分布";
    var neuter = 1 - video.goodPercent - video.badPercent;
    var unknown = 1 - video.manGoodPercent - video.womanGoodPercent;
    var allData = ["好评", "差评", "中性评价"];
    var sexData = ["男", "女", "未知"];

    var allValue = [video.goodPercent, video.badPercent, neuter];
    var sexValue = [video.manGoodPercent, video.womanGoodPercent, unknown];

    setOption(subStr, title, allData, allValue, chartAll);
    setOption(subStr, title, sexData, sexValue, chartSex);
}

//设置图形显示
function setOption(subStr, title, data, value, chart) {
    var option = {
        title: {
            text: title,
            subtext: subStr,
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: [data[0], data[1], data[2]]
        },
        toolbox: {
            show: true,
            feature: {
                // mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore: {show: true}
            }
        },
        calculable: true,
        series: [
            {
                // name: '情感分布',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: value[0], name: data[0]},
                    {value: value[1], name: data[1]},
                    {value: value[2], name: data[2]}
                ]
            }
        ]
    };
    chart.setOption(option);
}