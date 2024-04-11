(function(){function t(e){var k=e.jsustoolkitErrCode=e.jsustoolkitErrCode||{},a=e.asn1,l=e.pkcs7=e.pkcs7||{};l.messageFromPem=function(b){if(null==c||"undefined"==typeof c)throw{code:"113001",message:k["113001"]};b=e.pki.pemToDer(b);var c=a.fromDer(b);return l.messageFromAsn1(c)};l.messageToPem=function(b,c){if(null==b||"undefined"==typeof b)throw{code:"113002",message:k["113002"]};b=a.toDer(b.toAsn1());b=e.util.encode64(b.getBytes(),c||64);return"-----BEGIN PKCS7-----\r\n"+b+"\r\n-----END PKCS7-----"};l.messageFromBase64=function(b){if(null==b||"undefined"==typeof b)throw{code:"113003",message:k["113003"]};b=e.pki.base64ToDer(b);b=a.fromDer(b);return l.messageFromAsn1(b)};l.messageToBase64=function(b){if(null==b||"undefined"==typeof b)throw{code:"113004",message:k["113004"]};b=a.toDer(b.toAsn1());return e.util.encode64(b.getBytes())};l.messageFromAsn1=function(b){if(null==b||"undefined"==typeof b)throw{code:"113005",message:k["113005"]};var c={},d=[];if(!a.validate(b,l.asn1.contentInfoValidator,c,d))throw{code:"113006",message:k["113006"],errors:d};b=a.derToOid(c.contentType);switch(b){case e.pki.oids.envelopedData:b=l.createEnvelopedData();break;case e.pki.oids.encryptedData:b=l.createEncryptedData();break;case e.pki.oids.signedData:b=l.createSignedData();break;default:throw{code:"113007",message:k["113007"]+"("+b+")"};}b.fromAsn1(c.content.value[0]);return b};var p=function(b){var c={},d=[];if(!a.validate(b,l.asn1.recipientInfoValidator,c,d))throw{code:"113008",message:k["113008"],errors:d};return{version:c.version.charCodeAt(0),issuer:e.pki.RDNAttributesAsArray(c.issuer),serialNumber:e.util.createBuffer(c.serial).toHex(),encContent:{algorithm:a.derToOid(c.encAlgorithm),parameter:c.encParameter.value,content:c.encKey}}},r=function(b){return a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.INTEGER,!1,String.fromCharCode(b.version)),a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[e.pki.distinguishedNameToAsn1({attributes:b.issuer}),a.create(a.Class.UNIVERSAL,a.Type.INTEGER,!1,e.util.hexToBytes(b.serialNumber))]),a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(b.encContent.algorithm).getBytes()),a.create(a.Class.UNIVERSAL,a.Type.NULL,!1,"")]),a.create(a.Class.UNIVERSAL,a.Type.OCTETSTRING,!1,b.encContent.content)])},t=function(a){for(var b=[],d=0;d<a.length;d++)b.push(r(a[d]));return b},q=function(b){return[a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(e.pki.oids.data).getBytes()),a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(b.algorithm).getBytes()),a.create(a.Class.UNIVERSAL,a.Type.OCTETSTRING,!1,b.parameter.getBytes())]),a.create(a.Class.CONTEXT_SPECIFIC,0,!1,b.content.getBytes())]},u=function(b,c,d){if(null==b||"undefined"==typeof b)throw{code:"113009",message:k["113009"]};if(null==c||"undefined"==typeof c)throw{code:"113010",message:k["113010"]};if(null==d||"undefined"==typeof d)throw{code:"113011",message:k["113011"]};var f={},g=[];if(!a.validate(c,d,f,g))throw{code:"113012",message:k["113012"],errors:g};if(a.derToOid(f.contentType)!==e.pki.oids.data)throw{code:"113013",message:k["113013"]};if(f.encContent){c="";if(f.encContent.constructor===Array)for(d=0;d<f.encContent.length;++d){if(f.encContent[d].type!==a.Type.OCTETSTRING)throw{code:"113014",message:k["113014"]};c+=f.encContent[d].value}else c=f.encContent;b.encContent={algorithm:a.derToOid(f.encAlgorithm),parameter:e.util.createBuffer(f.encParameter.value),content:e.util.createBuffer(c)}}if(f.content){c="";if(f.content.constructor===Array)for(d=0;d<f.content.length;++d){if(f.content[d].type!==a.Type.OCTETSTRING)throw{code:"113015",message:k["113015"]};c+=f.content[d].value}else c=f.content.constructor===Object?f.content.value[0].value:f.content;b.content=e.util.createBuffer(c)}if(f.signature){for(d=0;d<f.certificates.length;++d)b.certificates.push(e.pki.certificateFromAsn1(f.certificates[d]));b.crls=f.crls;b.signContent.push(f.signerInfos);b.digestAlgo.push(a.derToOid(f.digestAlgorithm));b.authenticatedAttributes.push(f.authenticatedAttributes);b.signature.push(f.signature)}b.version=f.version.charCodeAt(0);return b.rawCapture=f},v=function(a){if(null==a||"undefined"==typeof a)throw{code:"113016",message:k["113016"]};if(void 0===a.encContent.key)throw{code:"113017",message:k["113017"]};if(void 0===a.content){switch(a.encContent.algorithm){case e.pki.oids["aes128-CBC"]:case e.pki.oids["aes192-CBC"]:case e.pki.oids["aes256-CBC"]:var b=e.aes.createDecryptionCipher(a.encContent.key);break;case e.pki.oids["des-EDE3-CBC"]:b=e.des.createDecryptionCipher(a.encContent.key);break;case e.pki.oids["seed-CBC"]:b=e.seed.createDecryptionCipher(a.encContent.key);break;default:throw{code:"113018",message:k["113018"]+a.encContent.algorithm};}b.start(a.encContent.parameter);b.update(a.encContent.content);if(!b.finish())throw{code:"113019",message:k["113019"]};a.content=b.output}},w=function(a,c,d){if(null==a||"undefined"==typeof a)throw{code:"113020",message:k["113020"]};if(void 0===a.encContent.content){d=d||a.encContent.algorithm;c=c||a.encContent.key;var b;switch(d){case e.pki.oids["aes128-CBC"]:var g=b=16;var h=e.aes.createEncryptionCipher;break;case e.pki.oids["aes192-CBC"]:b=24;g=16;h=e.aes.createEncryptionCipher;break;case e.pki.oids["aes256-CBC"]:b=32;g=16;h=e.aes.createEncryptionCipher;break;case e.pki.oids["des-EDE3-CBC"]:b=24;g=8;h=e.des.createEncryptionCipher;break;case e.pki.oids["seed-CBC"]:g=b=16;h=e.seed.createEncryptionCipher;break;default:throw{code:"113021",message:k["113021"]+d};}if(void 0===c)c=e.util.createBuffer(e.random.getBytes(b));else if(c.length()!=b)throw{code:"113022",message:k["113022"]+"got "+c.length()+" bytes, expected "+b};a.encContent.algorithm=d;a.encContent.key=c;a.encContent.parameter=e.util.createBuffer(e.random.getBytes(g));c=h(c);c.start(a.encContent.parameter.copy());c.update(a.content);if(!c.finish())throw{code:"113023",message:k["113023"]};a.encContent.content=c.output}},y=function(b){if(null==b||"undefined"==typeof b)throw{code:"113024",message:k["113024"]};var c=a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[]);c.value.push(a.create(a.Class.UNIVERSAL,a.Type.INTEGER,!1,String.fromCharCode(b.version)));c.value.push(a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[e.pki.distinguishedNameToAsn1({attributes:b.issuer}),a.create(a.Class.UNIVERSAL,a.Type.INTEGER,!1,e.util.hexToBytes(b.serialNumber))]));c.value.push(a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(b.digestAlgorithm).getBytes()),a.create(a.Class.UNIVERSAL,a.Type.NULL,!1,"")]));b.authAttr&&c.value.push(a.create(a.Class.CONTEXT_SPECIFIC,0,!0,b.authAttr.value));c.value.push(a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(b.digestEncAlgorithm).getBytes()),a.create(a.Class.UNIVERSAL,a.Type.NULL,!1,"")]));c.value.push(a.create(a.Class.UNIVERSAL,a.Type.OCTETSTRING,!1,b.signature));return c},x=function(b){if(null==b||"undefined"==typeof b)throw{code:"113025",message:k["113025"]};var c=a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[]);c.value.push(a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(e.pki.oids.contentType).getBytes()));c.value.push(a.create(a.Class.UNIVERSAL,a.Type.SET,!0,[a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(e.pki.oids.data).getBytes())]));var d=a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[]);d.value.push(a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(e.pki.oids.signingTime).getBytes()));d.value.push(a.create(a.Class.UNIVERSAL,a.Type.SET,!0,[a.create(a.Class.UNIVERSAL,a.Type.GENERALIZEDTIME,!1,a.dateToGeneralizedTime(b.signTime))]));var f=a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[]);f.value.push(a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(e.pki.oids.messageDigest).getBytes()));f.value.push(a.create(a.Class.UNIVERSAL,a.Type.SET,!0,[a.create(a.Class.UNIVERSAL,a.Type.OCTETSTRING,!1,b.digest)]));b=a.create(a.Class.UNIVERSAL,a.Type.SET,!0,[]);b.value.push(c);b.value.push(d);b.value.push(f);return b};l.createSignedData=function(){var b=null;return b={type:e.pki.oids.signedData,version:1,certificates:[],crls:[],signature:[],signContent:[],authenticatedAttributes:[],digestAlgo:[],fromAsn1:function(a){if(null==a||"undefined"==typeof a)throw{code:"113026",message:k["113026"]};u(b,a,l.asn1.signedDataValidator)},toAsn1:function(){var c=a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[]);c.value.push(a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(b.type).getBytes()));var d=a.create(a.Class.CONTEXT_SPECIFIC,0,!0,[]),f=a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[]);f.value.push(a.create(a.Class.UNIVERSAL,a.Type.INTEGER,!1,String.fromCharCode(b.version)));for(var g=a.create(a.Class.UNIVERSAL,a.Type.SET,!0,[]),h=0;h<b.signContent.length;h++)g.value.push(a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(b.signContent[h].digestAlgorithm).getBytes()),a.create(a.Class.UNIVERSAL,a.Type.NULL,!1,"")]));f.value.push(g);b.content?f.value.push(a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(e.pki.oids.data).getBytes()),a.create(a.Class.CONTEXT_SPECIFIC,0,!0,[a.create(a.Class.UNIVERSAL,a.Type.OCTETSTRING,!1,b.content.getBytes())])])):f.value.push(a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(e.pki.oids.data).getBytes())]));if(0!=b.certificates.length){g=a.create(a.Class.CONTEXT_SPECIFIC,0,!0,[]);for(h=0;h<b.certificates.length;h++)g.value.push(e.pki.certificateToAsn1(b.certificates[h]));f.value.push(g)}if(0!=b.crls.length){g=a.create(a.Class.CONTEXT_SPECIFIC,1,!0,[]);for(h=0;h<b.crls.length;h++)g.value.push(b.crls[h].getBytes());f.value.push(g)}if(0<b.signContent.length){g=a.create(a.Class.UNIVERSAL,a.Type.SET,!0,[]);for(h=0;h<b.signContent.length;h++)g.value.push(y(b.signContent[h]));f.value.push(g)}else throw{code:"113027",message:k["113027"]};d.value.push(f);c.value.push(d);return c},signWithDecPriKey:function(c,d,f,g,h,m,n){if(null==d||"undefined"==typeof d)throw{code:"113028",message:k["113028"]};if(null==f||"undefined"==typeof f)throw{code:"113029",message:k["113029"]};if(null==c||"undefined"==typeof c)c="";b.content=n?e.util.createBuffer(c,"utf8"):e.util.createBuffer(c);c={version:1,digestAlgorithm:e.pki.oids.sha256,attrState:!1};n=e.pki.oids[d.siginfo.algorithmOid].split("With");c.digestAlgorithm=e.pki.oids[n[0]];c.digestEncAlgorithm=e.pki.oids[n[1]];c.issuer=d.issuer.attributes;c.serialNumber=d.serialNumber;b.certificates.push(d);c.signTime=null!=g?g:new Date;null!=h&&b.crls.push(h);c.attrState=null!=m?m:!1;d=e.md.algorithms[e.pki.oids[c.digestAlgorithm]].create();d.update(b.content.bytes());1==c.attrState&&(c.digest=d.digest().bytes(),c.authAttr=x(c),d=e.md.algorithms[e.pki.oids[c.digestAlgorithm]].create(),g=a.toDer(c.authAttr),d.update(g.bytes()));c.signature=f.sign(d);b.signContent.push(c)},sign:function(a,d,f,g,h,m,n,l){if(null==g||"undefined"==typeof g)throw{code:"113030",message:k["113030"]};if(null==f||"undefined"==typeof f)throw{code:"113031",message:k["113031"]};f=e.pkcs8.decryptPrivateKeyInfo(f,g);f=e.pki.privateKeyFromAsn1(f);b.signWithDecPriKey(a,d,f,h,m,n,l?!1:!0)},signForTransfer:function(a,d,f,g){if(null==g||"undefined"==typeof g)throw{code:"113030",message:k["113030"]};if(null==f||"undefined"==typeof f)throw{code:"113031",message:k["113031"]};f=e.pkcs8.decryptPrivateKeyInfo(f,g);f=e.pki.privateKeyFromAsn1(f);b.signWithDecPriKey(a,d,f,null,null,null,!1)},signWithHashWithDecPriKey:function(c,d,f,g,h,l,n){if(null==f||"undefined"==typeof f)throw{code:"113028",message:k["113028"]};if(null==g||"undefined"==typeof g)throw{code:"113029",message:k["113029"]};if(null==c||"undefined"==typeof c)c="";var m={version:1,digestAlgorithm:e.pki.oids.sha256,attrState:!1},p=e.pki.oids[f.siginfo.algorithmOid].split("With");m.digestAlgorithm=e.pki.oids[d];m.digestEncAlgorithm=e.pki.oids[p[1]];m.issuer=f.issuer.attributes;m.serialNumber=f.serialNumber;b.certificates.push(f);m.signTime=null!=h?h:new Date;null!=l&&b.crls.push(l);m.attrState=null!=n?n:!1;m.digest=c;1==m.attrState?(m.authAttr=x(m),md=e.md.algorithms[e.pki.oids[m.digestAlgorithm]].create(),c=a.toDer(m.authAttr),md.update(c.bytes()),m.signature=g.sign(md)):m.signature=g.signWithHash(c);b.signContent.push(m)},signWithHashData:function(a,d,f,g,h,m,n,l){if(null==h||"undefined"==typeof h)throw{code:"113030",message:k["113030"]};if(null==g||"undefined"==typeof g)throw{code:"113031",message:k["113031"]};g=e.pkcs8.decryptPrivateKeyInfo(g,h);g=e.pki.privateKeyFromAsn1(g);b.signWithHashWithDecPriKey(a,d,f,g,m,n,l)},signWithHashDataNP1:function(a,d,f,g,h,m){if(null==g||"undefined"==typeof g)throw{code:"113028",message:k["113028"]};if(null==d||"undefined"==typeof d)d="";if(null==a||"undefined"==typeof a)a="";var c={version:1,digestAlgorithm:e.pki.oids.sha256},l=e.pki.oids[g.siginfo.algorithmOid].split("With");c.digestAlgorithm=e.pki.oids[f];c.digestEncAlgorithm=e.pki.oids[l[1]];c.issuer=g.issuer.attributes;c.serialNumber=g.serialNumber;b.certificates.push(g);c.signTime=null!=h?h:new Date;null!=m&&b.crls.push(m);c.digest=d;c.signature=a;b.signContent.push(c)},signWithP1:function(a,d,f,g,h){if(null==f||"undefined"==typeof f)throw{code:"113028",message:k["113028"]};if(null==d||"undefined"==typeof d)d="";b.content=e.util.createBuffer(d,"utf8");d={version:1,digestAlgorithm:e.pki.oids.sha256};var c=e.pki.oids[f.siginfo.algorithmOid].split("With");d.digestAlgorithm=e.pki.oids[c[0]];d.digestEncAlgorithm=e.pki.oids[c[1]];d.issuer=f.issuer.attributes;d.serialNumber=f.serialNumber;b.certificates.push(f);d.signTime=null!=g?g:new Date;null!=h&&b.crls.push(h);f=e.md.algorithms[e.pki.oids[d.digestAlgorithm]].create();f.update(b.content.bytes());d.digest=f.digest().bytes();d.signature=a;b.signContent.push(d)},verifyWithHash:function(c){if("undefined"!=typeof certs)if(cert.constructor===Array)for(var d in certs)b.certificates.push(certs[d]);else b.certificates.push(certs);if(null==c||"undefined"==typeof c)throw{code:"113043",message:k["113043"]};if(b.certificates.length!=b.signContent.length)throw{code:"113042",message:k["113042"]};for(d=0;d<b.certificates.length;d++){var f=b.certificates[d].publicKey;if("undefined"!=typeof b.authenticatedAttributes[0]){var g=a.create(a.Class.UNIVERSAL,a.Type.SET,!0,[]);for(var h in b.authenticatedAttributes[d])e.pki.oids.messageDigest!=a.derToOid(b.authenticatedAttributes[d][h].value[0].value)&&e.pki.oids.signingTime==a.derToOid(b.authenticatedAttributes[d][h].value[0].value)&&(23==b.authenticatedAttributes[d][h].value[1].value[0].type?b.signTime=a.utcTimeToDate(b.authenticatedAttributes[d][h].value[1].value[0].value):24==b.authenticatedAttributes[d][h].value[1].value[0].type&&(b.signTime=a.generalizedTimeToDate(b.authenticatedAttributes[d][h].value[1].value[0].value))),g.value.push(b.authenticatedAttributes[d][h]);var m=e.md.algorithms[e.pki.oids[b.digestAlgo[d]]].create();m.update(a.toDer(g).bytes());b.verifyResult=!1;try{b.verifyResult=f.verify(m.digest().getBytes(),b.signature[d])}catch(n){b.verifyResult=!1}}else{g=c;try{b.verifyResult=f.verify(g,b.signature[d])}catch(n){b.verifyResult=!1}}}},addSign:function(a,b,e){},verify:function(c,d){if("undefined"!=typeof c)if(cert.constructor===Array)for(var f in c)b.certificates.push(c[f]);else b.certificates.push(c);"undefined"!=typeof d&&(b.content=e.util.createBuffer(d,"utf8"));if(b.certificates.length!=b.signContent.length)throw{code:"113042",message:k["113042"]};for(f=0;f<b.certificates.length;f++){c=b.certificates[f].publicKey;if("undefined"!=typeof b.authenticatedAttributes[0]){var g=a.create(a.Class.UNIVERSAL,a.Type.SET,!0,[]);for(var h in b.authenticatedAttributes[f]){if(e.pki.oids.messageDigest==a.derToOid(b.authenticatedAttributes[f][h].value[0].value))var m=b.authenticatedAttributes[f][h].value[1].value[0].value;else e.pki.oids.signingTime==a.derToOid(b.authenticatedAttributes[f][h].value[0].value)&&(23==b.authenticatedAttributes[f][h].value[1].value[0].type?b.signTime=a.utcTimeToDate(b.authenticatedAttributes[f][h].value[1].value[0].value):24==b.authenticatedAttributes[f][h].value[1].value[0].type&&(b.signTime=a.generalizedTimeToDate(b.authenticatedAttributes[f][h].value[1].value[0].value)));g.value.push(b.authenticatedAttributes[f][h])}d=e.md.algorithms[e.pki.oids[b.digestAlgo]].create();d.update(b.content.bytes());d.digest().bytes()==m?b.verifyResult=!0:b.verifyResult=!1;g=a.toDer(g).bytes()}else g=b.content.bytes();d=e.md.algorithms[e.pki.oids[b.digestAlgo[f]]].create();d.update(g);b.verifyResult=!1;try{b.verifyResult=c.verify(d.digest().getBytes(),b.signature[f])}catch(n){b.verifyResult=!1}}}}};l.createEncryptedData=function(){var b=null;return b={type:e.pki.oids.encryptedData,version:0,encContent:{algorithm:e.pki.oids["seed-CBC"]},fromAsn1:function(a){if(null==a||"undefined"==typeof a)throw{code:"113032",message:k["113032"]};u(b,a,l.asn1.encryptedDataValidator)},toAsn1:function(){return a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(b.type).getBytes()),a.create(a.Class.CONTEXT_SPECIFIC,0,!0,[a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.INTEGER,!1,String.fromCharCode(b.version)),a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,q(b.encContent))])])])},encrypt:function(a,d){if(null==a||"undefined"==typeof a)throw{code:"113033",message:k["113033"]};a=e.util.createBuffer(a);w(b,a,d)},decrypt:function(a){if(null==a||"undefined"==typeof a)throw{code:"113034",message:k["113034"]};a=e.util.createBuffer(a);b.encContent.key=a;v(b)}}};l.createEnvelopedData=function(){var b=null;return b={type:e.pki.oids.envelopedData,version:0,recipients:[],encContent:{algorithm:e.pki.oids["seed-CBC"]},fromAsn1:function(a){if(null==a||"undefined"==typeof a)throw{code:"113035",message:k["113035"]};var c=u(b,a,l.asn1.envelopedDataValidator);a=b;c=c.recipientInfos.value;for(var e=[],g=0;g<c.length;g++)e.push(p(c[g]));a.recipients=e},toAsn1:function(){return a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.OID,!1,a.oidToDer(b.type).getBytes()),a.create(a.Class.CONTEXT_SPECIFIC,0,!0,[a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,[a.create(a.Class.UNIVERSAL,a.Type.INTEGER,!1,String.fromCharCode(b.version)),a.create(a.Class.UNIVERSAL,a.Type.SET,!0,t(b.recipients)),a.create(a.Class.UNIVERSAL,a.Type.SEQUENCE,!0,q(b.encContent))])])])},findRecipient:function(a){if(null==a||"undefined"==typeof a)throw{code:"113036",message:k["113036"]};for(var c=a.issuer.attributes,e=0;e<b.recipients.length;++e){var g=b.recipients[e],h=g.issuer;if(g.serialNumber===a.serialNumber&&h.length===c.length){for(var m=!0,l=0;l<c.length;++l)if(h[l].type!==c[l].type||h[l].value!==c[l].value){m=!1;break}if(m)return g}}},decrypt:function(a,d){if(null==a||"undefined"==typeof a)throw{code:"113037",message:k["113037"]};if(null==d||"undefined"==typeof d)throw{code:"113038",message:k["113038"]};if(void 0===b.encContent.key&&void 0!==a&&void 0!==d)switch(a.encContent.algorithm){case e.pki.oids.RSAEncryption:a=d.decrypt(a.encContent.content);b.encContent.key=e.util.createBuffer(a);break;default:throw{code:"113039",message:k["113039"]+"( OID : "+a.encContent.algorithm+")"};}v(b)},addRecipient:function(a){if(null==a||"undefined"==typeof a)throw{code:"113040",message:k["113040"]};b.recipients.push({version:0,issuer:a.issuer.attributes,serialNumber:a.serialNumber,encContent:{algorithm:e.pki.oids.RSAEncryption,key:a.publicKey}})},encrypt:function(a,d){w(b,a,d);for(a=0;a<b.recipients.length;a++)if(d=b.recipients[a],void 0===d.encContent.content)switch(d.encContent.algorithm){case e.pki.oids.RSAEncryption:d.encContent.content=d.encContent.key.encrypt(b.encContent.key.data);break;default:throw{code:"113041",message:k["113041"]};}}}}}var q="./aes ./seed ./asn1 ./des ./pkcs7asn1 ./pki ./random ./util ./jsustoolkitErrCode".split(" "),r=null;"function"!==typeof define&&("object"===typeof module&&module.exports?r=function(e,k){k(require,module)}:(crosscert=window.crosscert=window.crosscert||{},t(crosscert)));(r||"function"===typeof define)&&(r||define)(["require","module"].concat(q),function(e,k){k.exports=function(a){var k=q.map(function(a){return e(a)}).concat(t);a=a||{};a.defined=a.defined||{};if(a.defined.pkcs7)return a.pkcs7;a.defined.pkcs7=!0;for(var p=0;p<k.length;++p)k[p](a);return a.pkcs7}})})();