(function(){function t(d){var w=d.jsustoolkitErrCode=d.jsustoolkitErrCode||{},e=d.md5=d.md5||{};d.md=d.md||{};d.md.algorithms=d.md.algorithms||{};d.md.md5=d.md.algorithms.md5=e;var u=null,n=null,q=null,p=null,l=!1,t=function(){u=String.fromCharCode(128);u+=d.util.fillString(String.fromCharCode(0),64);n=[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,6,11,0,5,10,15,4,9,14,3,8,13,2,7,12,5,8,11,14,1,4,7,10,13,0,3,6,9,12,15,2,0,7,14,5,12,3,10,1,8,15,6,13,4,11,2,9];q=[7,12,17,22,7,12,17,22,7,12,17,22,7,12,17,22,5,9,14,20,5,9,14,20,5,9,14,20,5,9,14,20,4,11,16,23,4,11,16,23,4,11,16,23,4,11,16,23,6,10,15,21,6,10,15,21,6,10,15,21,6,10,15,21];p=Array(64);for(var f=0;64>f;++f)p[f]=Math.floor(4294967296*Math.abs(Math.sin(f+1)));l=!0},r=function(f,d,e){for(var a,c,b,h,k,m,g,v=e.length();64<=v;){c=f.h0;b=f.h1;h=f.h2;k=f.h3;for(g=0;16>g;++g)d[g]=e.getInt32Le(),a=k^b&(h^k),a=c+a+p[g]+d[g],m=q[g],c=k,k=h,h=b,b+=a<<m|a>>>32-m;for(;32>g;++g)a=h^k&(b^h),a=c+a+p[g]+d[n[g]],m=q[g],c=k,k=h,h=b,b+=a<<m|a>>>32-m;for(;48>g;++g)a=b^h^k,a=c+a+p[g]+d[n[g]],m=q[g],c=k,k=h,h=b,b+=a<<m|a>>>32-m;for(;64>g;++g)a=h^(b|~k),a=c+a+p[g]+d[n[g]],m=q[g],c=k,k=h,h=b,b+=a<<m|a>>>32-m;f.h0=f.h0+c&4294967295;f.h1=f.h1+b&4294967295;f.h2=f.h2+h&4294967295;f.h3=f.h3+k&4294967295;v-=64}};e.create=function(){l||t();var f=null,e=d.util.createBuffer(),n=Array(16),a={algorithm:"md5",blockLength:64,digestLength:16,messageLength:0,start:function(){a.messageLength=0;e=d.util.createBuffer();f={h0:1732584193,h1:4023233417,h2:2562383102,h3:271733878}}};a.start();a.update=function(c,b){if(null==c)throw{code:"102021",message:w["102021"]};"utf8"===b&&(c=d.util.encodeUtf8(c));a.messageLength+=c.length;e.putBytes(c);r(f,n,e);(2048<e.read||0===e.length())&&e.compact()};a.digest=function(){var c=a.messageLength,b=d.util.createBuffer();b.putBytes(e.bytes());b.putBytes(u.substr(0,64-(c+8)%64));b.putInt32Le(c<<3&4294967295);b.putInt32Le(c>>>29&255);c={h0:f.h0,h1:f.h1,h2:f.h2,h3:f.h3};r(c,n,b);b=d.util.createBuffer();b.putInt32Le(c.h0);b.putInt32Le(c.h1);b.putInt32Le(c.h2);b.putInt32Le(c.h3);return b};return a}}var r=["./util","./jsustoolkitErrCode"],l=null;"function"!==typeof define&&("object"===typeof module&&module.exports?l=function(d,l){l(require,module)}:(crosscert=window.crosscert=window.crosscert||{},t(crosscert)));(l||"function"===typeof define)&&(l||define)(["require","module"].concat(r),function(d,l){l.exports=function(e){var l=r.map(function(e){return d(e)}).concat(t);e=e||{};e.defined=e.defined||{};if(e.defined.md5)return e.md5;e.defined.md5=!0;for(var n=0;n<l.length;++n)l[n](e);return e.md5}})})();