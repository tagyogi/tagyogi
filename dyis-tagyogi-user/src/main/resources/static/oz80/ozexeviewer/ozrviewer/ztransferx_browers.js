var ZTransferXObject;

Initialize_ZT = function(ObjectID, ClassID, Width, Height, CodeBase) {
    if(navigator.appName == "Microsoft Internet Explorer" || (navigator.userAgent.indexOf("Trident") > -1)) {
        if(navigator.appVersion.indexOf("MSIE 6") > -1) {
            ZTransferXObject = document.createElement('<object ID = "' + ObjectID + '" width = "' + Width + '" height = "' + Height + '" classid = "' + ClassID + '" codebase = "' + CodeBase + '"></object>');
        } else {
            strZTransferXObject = '<object ID = "' + ObjectID + '" width = "' + Width + '" height = "' + Height + '" classid = "' + ClassID + '" codebase = "' + CodeBase + '">';
        }
    }
}

function Insert_ZT_Param(ParameterName, ParameterValue) {
    if(navigator.appName == "Microsoft Internet Explorer" || (navigator.userAgent.indexOf("Trident") > -1)) {
        if(navigator.appVersion.indexOf("MSIE 6") > -1) {
            var ZTransferXParam = document.createElement('<param name = "' + ParameterName + '" value = "' + ParameterValue + '">');
            ZTransferXObject.appendChild(ZTransferXParam);
        } else {
            strZTransferXObject += '<param name = "' + ParameterName + '" value = "' + ParameterValue + '"/>';
        }
    }
}

function Start_ZT() {
    if((navigator.appName == "Microsoft Internet Explorer"  || (navigator.userAgent.indexOf("Trident") > -1))&& navigator.appVersion.indexOf("MSIE 6") == -1) {
        strZTransferXObject += '</object>';
        InstallOZViewer.innerHTML = strZTransferXObject;
        return;
    }
    InstallOZViewer.appendChild(ZTransferXObject);
}
