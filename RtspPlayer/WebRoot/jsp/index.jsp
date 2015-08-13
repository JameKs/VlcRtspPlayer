<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script>
var itemId = 0;
function getVLC(name)
{
        if (window.document[name])    
        {
                return window.document[name];
        }
        if (navigator.appName.indexOf("Microsoft Internet")==-1)
        {
                if (document.embeds && document.embeds[name])
                        return document.embeds[name];    
        }
        else
        {
                return document.getElementById(name);
        }
}

function doGo(mrl)
{
        var vlc = getVLC("vlc");
        itemId=vlc.playlist.add(mrl);
        vlc.playlist.playItem(itemId);
        document.getElementById("btn_stop").disabled = false;
}

function updateVolume(deltaVol)
{
        var vlc = getVLC("vlc");
        vlc.audio.volume += deltaVol;
}

function doPlay()
{
        vlc.playlist.playItem(itemId);
        
        document.getElementById("btn_stop").disabled = false;
        document.getElementById("btn_play").disabled = true;
}

function doStop()
{
        getVLC("vlc").playlist.stop();
        document.getElementById("btn_stop").disabled = true;
        document.getElementById("btn_play").disabled = false;
}
</script>
</head>
<body>
<div style="margin: 50px">
        <a title="rtsp://huang1987zl.imwork.net:12213/Video/test.264" href="#" onclick="doGo(this.title);return false;">01.ts</a>
        <span style="margin: 20px;" />
        <a title="rtsp://huang1987zl.imwork.net:12213/Video/test.264" href="#" onclick="doGo(this.title);return false;">02.mp3</a>
        <span style="margin: 20px;" />
</div>
<div>
        <object classid="clsid:9be31822-fdad-461b-ad51-be1d1c159921"
                width="640"
                height="480"
                id="vlc"
                events="true">
        <param name="mrl" value="" />
        <param name="showdisplay" value="true" />
        <param name="autoloop" value="false" />
        <param name="autoplay" value="false" />
        <param name="volume" value="50" />
        <param name="starttime" value="0" />
        <embed pluginspage="http://www.videolan.org"
                     type="application/x-vlc-plugin"
                     version="videolan.vlcplugin.2"
                     width="640"
                     height="480"
                     name="vlc">
        </embed>
        </object>
        

</div>
<div>
<input type=button id="btn_play" value=" 播放 " onClick='doPlay();' disabled="true">
<input type=button id="btn_stop" value="停止" onClick='doStop();' disabled="true">
<input type=button value="静音切换" onclick='getVLC("vlc").audio.togglemute();'>
<input type=button value="减小音量" onclick='updateVolume(-10)'>
<input type=button value="增加音量" onclick='updateVolume(+10)'>
</div>
</body>
</html>