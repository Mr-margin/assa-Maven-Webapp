var map = new BMap.Map("container",{enableMapClick:false}); // 创建地图实例
//map.setMaxZoom(19);
//map.setMinZoom(13);
map.enableScrollWheelZoom(true);
//map.addControl(new BMap.NavigationControl());
//map.addControl(new BMap.ScaleControl());

var mapType1 = new BMap.MapTypeControl({
	mapTypes : [ BMAP_NORMAL_MAP, BMAP_HYBRID_MAP ]
});
map.addControl(mapType1);

var point = new BMap.Point(109.727775, 39.575628);
map.centerAndZoom(point, 13);

//创建地址解析器实例
//var myGeo = new BMap.Geocoder();




