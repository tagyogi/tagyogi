(function(){function y(b){var g=b.jsustoolkitErrCode=b.jsustoolkitErrCode||{},B=b.aes=b.aes||{};b.cipher=b.cipher||{};b.cipher.algorithms=b.cipher.algorithms||{};b.cipher.aes=b.cipher.algorithms.aes=B;var v=!1,m,C,x,w,u,y=function(){v=!0;x=[0,1,2,4,8,16,32,64,128,27,54];for(var a=Array(256),d=0;128>d;++d)a[d]=d<<1,a[d+128]=d+128<<1^283;m=Array(256);C=Array(256);w=Array(4);u=Array(4);for(d=0;4>d;++d)w[d]=Array(256),u[d]=Array(256);var c=0,b=0;for(d=0;256>d;++d){var l=b^b<<1^b<<2^b<<3^b<<4;l=l>>8^l&255^99;m[c]=l;C[l]=c;var n=a[l];var e=a[c];var h=a[e];var k=a[h];n^=n<<24^l<<16^l<<8^l;h=(e^h^k)<<24^(c^k)<<16^(c^h^k)<<8^c^e^k;for(var f=0;4>f;++f)w[f][c]=n,u[f][l]=h,n=n<<24|n>>>8,h=h<<24|h>>>8;0===c?c=b=1:(c=e^a[a[a[e^k]]],b^=a[a[b]])}},z=function(a,d){a=a.slice(0);for(var c,b=1,l=a.length,n=4*(l+6+1),e=l;e<n;++e)c=a[e-1],0===e%l?(c=m[c>>>16&255]<<24^m[c>>>8&255]<<16^m[c&255]<<8^m[c>>>24]^x[b]<<24,b++):6<l&&4==e%l&&(c=m[c>>>24]<<24^m[c>>>16&255]<<16^m[c>>>8&255]<<8^m[c&255]),a[e]=a[e-l]^c;if(d){c=u[0];b=u[1];l=u[2];var h=u[3],k=a.slice(0);n=a.length;e=0;for(var f=n-4;e<n;e+=4,f-=4)if(0===e||e===n-4)k[e]=a[f],k[e+1]=a[f+3],k[e+2]=a[f+2],k[e+3]=a[f+1];else for(var p=0;4>p;++p)d=a[f+p],k[e+(3&-p)]=c[m[d>>>24]]^b[m[d>>>16&255]]^l[m[d>>>8&255]]^h[m[d&255]];a=k}return a},E=function(a,d,b,g){var c=a.length/4-1;if(g){var n=u[0];var e=u[1];var h=u[2];var k=u[3];var f=C}else n=w[0],e=w[1],h=w[2],k=w[3],f=m;var p=d[0]^a[0];var q=d[g?3:1]^a[1];var t=d[2]^a[2];d=d[g?1:3]^a[3];for(var r=3,D=1;D<c;++D){var F=n[p>>>24]^e[q>>>16&255]^h[t>>>8&255]^k[d&255]^a[++r];var B=n[q>>>24]^e[t>>>16&255]^h[d>>>8&255]^k[p&255]^a[++r];var v=n[t>>>24]^e[d>>>16&255]^h[p>>>8&255]^k[q&255]^a[++r];d=n[d>>>24]^e[p>>>16&255]^h[q>>>8&255]^k[t&255]^a[++r];p=F;q=B;t=v}b[0]=f[p>>>24]<<24^f[q>>>16&255]<<16^f[t>>>8&255]<<8^f[d&255]^a[++r];b[g?3:1]=f[q>>>24]<<24^f[t>>>16&255]<<16^f[d>>>8&255]<<8^f[p&255]^a[++r];b[2]=f[t>>>24]<<24^f[d>>>16&255]<<16^f[p>>>8&255]<<8^f[q&255]^a[++r];b[g?1:3]=f[d>>>24]<<24^f[p>>>16&255]<<16^f[q>>>8&255]<<8^f[t&255]^a[++r]},A=function(a,d){var c=null;if(null==a||"undefined"==typeof a)throw{code:"100011",message:g["100011"]};v||y();if(a.constructor==String&&(16==a.length||32==a.length))a=b.util.createBuffer(a);else if(a.constructor==Array&&(16==a.length||32==a.length)){var m=a;a=b.util.createBuffer();for(var l=0;l<m.length;++l)a.putByte(m[l])}else if(a.constructor==String||16!=a.length()&&32!=a.length())throw{code:"100012",message:g["100012"]};if(a.constructor!=Array){m=a;a=[];var n=m.length();if(16==n||32==n)for(n>>>=2,l=0;l<n;++l)a.push(m.getInt32())}if(a.constructor==Array&&(4==a.length||8==a.length)){var e=z(a,d),h,k,f,p,q,t;c={output:null,start:function(a,d){if(null==a||"undefined"==typeof a)throw{code:"100015",message:g["100015"]};if(16!=a.length)throw{code:"100016",message:g["100016"]};if(a.constructor==String&&16==a.length)a=b.util.createBuffer(a);else if(a.constructor==Array&&16==a.length){var r=a;a=b.util.createBuffer();for(var e=0;16>e;++e)a.putByte(r[e])}a.constructor!=Array&&(r=a,a=Array(4),a[0]=r.getInt32(),a[1]=r.getInt32(),a[2]=r.getInt32(),a[3]=r.getInt32());h=b.util.createBuffer();k=d||b.util.createBuffer();q=a.slice(0);f=Array(4);p=Array(4);t=!1;c.output=k},update:function(a){if(null==h&&null==k)throw{code:"100014",message:g["100014"]};null!=a&&a.constructor==String&&(a=b.util.createBuffer(a));if(!t){if(null==a||"undefined"==typeof a)throw{code:"100013",message:g["100013"]};h.putBuffer(a)}for(a=d&&!t?32:16;h.length()>=a;){if(d)for(var c=0;4>c;++c)f[c]=h.getInt32();else for(c=0;4>c;++c)f[c]=q[c]^h.getInt32();E(e,f,p,d);if(d){for(c=0;4>c;++c)k.putInt32(q[c]^p[c]);q=f.slice(0)}else{for(c=0;4>c;++c)k.putInt32(p[c]);q=p}}},finish:function(a){var b=!0;if(!d)if(a)b=a(d,16,h);else{var e=16==h.length()?16:16-h.length();h.fillWithByte(e,e)}b&&(t=!0,c.update());if(d)if(b=0===h.length())a?b=a(d,16,k):(a=k.length(),a=k.at(a-1),16<a?b=!1:k.truncate(a));else throw{code:"100017",message:g["100017"]};return b},tmonetpadding:function(a,d,c){if(a){a=c.length();var b=0,e;for(e=1;e<d+1;e++){var f=c.at(a-e);if(128==f){b++;break}else if(0==f)b++;else break}c.truncate(b);return!0}}}}return c};b.aes.startEncrypting=function(a,b,c){a=A(a,!1);a.start(b,c);return a};b.aes.createEncryptionCipher=function(a){return A(a,!1)};b.aes.startDecrypting=function(a,b,c){a=A(a,!0);a.start(b,c);return a};b.aes.createDecryptionCipher=function(a){return A(a,!0)};b.aes._expandKey=function(a,b){v||y();return z(a,b)};b.aes._updateBlock=E}var z=["./util","./jsustoolkitErrCode"],x=null;"function"!==typeof define&&("object"===typeof module&&module.exports?x=function(b,g){g(require,module)}:(crosscert=window.crosscert=window.crosscert||{},y(crosscert)));(x||"function"===typeof define)&&(x||define)(["require","module"].concat(z),function(b,g){g.exports=function(g){var v=z.map(function(g){return b(g)}).concat(y);g=g||{};g.defined=g.defined||{};if(g.defined.aes)return g.aes;g.defined.aes=!0;for(var m=0;m<v.length;++m)v[m](g);return g.aes}})})();