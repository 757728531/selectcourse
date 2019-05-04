
//提出定位方法
function he(lat,lng,addr){
	var center = new qq.maps.LatLng(lat,lng);
	var map = new qq.maps.Map(document.getElementById('container'), {
		center: center,
		zoom: 14,
	});
	//创建标记
	var marker = new qq.maps.Marker({
		position: center,
		map: map
	});
	//添加到提示窗
	var info = new qq.maps.InfoWindow({ map: map });
	//获取标记的点击事件
	qq.maps.event.addListener(marker, 'click', function() {
		info.open();
		info.setContent('<div style="text-align:center;font-size: larger;white-space:nowrap;margin:10px;">'+addr+'</div>');
		info.setPosition(center);
	});
}
//初始化加载地图，
window.onload = function(){

	var lat=31.707112;
	var lng=118.890924;
	var addr="南京航空航天大学金城学院";
	he(lat,lng,addr);
};
//点击事件，调用腾讯地图API定位
var geolocation = new qq.maps.Geolocation("RGCBZ-CG634-2CPUV-XUICH-7PMHE-ZPBKM", "myapp");
var options = {timeout: 8000};
function showPosition(position) {
  	var jsonlat = JSON.stringify(position, null, 4);
  	var objectJson = JSON.parse(jsonlat);
   	var lat=objectJson.lat;
   	var lng=objectJson.lng;
   	var addr=objectJson.addr;
    he(lat,lng,addr);
	document.getElementById("address").innerHTML = objectJson.province+objectJson.city+objectJson.addr;
};	