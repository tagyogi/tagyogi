(function(){function h(d){(d.pkcs5=d.pkcs5||{}).pbkdf2=function(b,c,p,e,a){if("undefined"===typeof a||null===a)a=d.md.sha1.create();var k=a.digestLength;if(e>4294967295*k)throw{message:"Derived key is too long."};var g=Math.ceil(e/k);e-=(g-1)*k;var f=d.hmac.create();f.start(a,b);b="";for(var l,m=1;m<=g;++m){f.update(c);f.update(d.util.int32ToBytes(m));a=l=f.digest().getBytes();for(var h=2;h<=p;++h)f.start(null,null),f.update(l),l=f.digest().getBytes(),a=d.util.xorBytes(a,l,k);b+=m<g?a:a.substr(0,
e)}return b}}var n=["./hmac","./md","./util"],g=null;"function"!==typeof define&&("object"===typeof module&&module.exports?g=function(d,b){b(require,module)}:(crosscert=window.crosscert=window.crosscert||{},h(crosscert)));(g||"function"===typeof define)&&(g||define)(["require","module"].concat(n),function(d,b){b.exports=function(c){var b=n.map(function(a){return d(a)}).concat(h);c=c||{};c.defined=c.defined||{};if(c.defined.pbkdf2)return c.pbkdf2;c.defined.pbkdf2=!0;for(var e=0;e<b.length;++e)b[e](c);
return c.pbkdf2}})})();
