var encryptKey = "Why are you so niubi??";
var encryptIV = "12345678";
var cookieName = "cookieofpeashoot";

function changeCookie(name, info) {
    let cookie = getCookie(cookieName);
    var map = {};
    if (cookie != "") {
        var decode = des_decrypt(cookie, encryptKey, encryptIV);
        map = JSON.parse(decode);
    }
    map[name] = info;
    var json = JSON.stringify(map);
    var encrypt = des_encrypt(json, encryptKey, encryptIV);
    setCookie(cookieName, encrypt, 60);
    location.reload();
}

function des_encrypt(str, key, iv) {
    var crypto_key = CryptoJS.enc.Utf8.parse(key);
    var crypto_iv = CryptoJS.enc.Utf8.parse(iv.substr(0, 8));
    try {
        var encode_str = CryptoJS.TripleDES.encrypt(str, crypto_key, {
            iv: crypto_iv,
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
    } catch (ex) {
        console.log(ex);
    }
    return encode_str.toString();
}

function des_decrypt(str, key, iv) {
    var crypto_key = CryptoJS.enc.Utf8.parse(key);
    var crypto_iv = CryptoJS.enc.Utf8.parse(iv.substr(0, 8));
    var decrypt_str = CryptoJS.TripleDES.decrypt(str, crypto_key, {
        iv: crypto_iv,
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return decrypt_str.toString(CryptoJS.enc.Utf8);
}

function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=")
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1
            c_end = document.cookie.indexOf(";", c_start)
            if (c_end == -1) c_end = document.cookie.length
            return unescape(document.cookie.substring(c_start, c_end))
        }
    }
    return ""
}

function setCookie(c_name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getDate() + expiredays)
    document.cookie = c_name + "=" + escape(value) +
        ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString()) + "; path=/"
}