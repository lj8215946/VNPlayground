var root;

page({
    onReady: function(){
        root = vn.dom.getElementById("root");
        console.log("root:"+root);
    },
    onClick1: function (params) {
        root.setProperty("class","");
    },
    onClick2: function (params) {
        root.setProperty("class","skin_a");
    },
    onClick3: function (params) {
        root.setProperty("class","skin_b");
    }
});