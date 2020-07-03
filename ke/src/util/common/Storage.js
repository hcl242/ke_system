
let Local = window.localStorage
let Session = window.sessionStorage

//local set
Local.set = function(key, value, duration) {
        var data = {
            value: value,
            expiryTime: !duration || isNaN(duration) ? 0 : this.getCurrentTimeStamp() + parseInt(duration)
        };
        this[key] = JSON.stringify(data);
    }

//local get
Local.get = function(key) {
        var data = this[key];
        if (!data || data === "null") {
            return null;
        }
        var now = this.getCurrentTimeStamp();
        var obj;
        try {
            obj = JSON.parse(data);
        } catch (e) {
            return null;
        }
        if (obj.expiryTime === 0 || obj.expiryTime > now) {
            return obj.value;
        }
        return null;
    }

//local remove
Local.remove = function(key){
        this.removeItem(key);
    }

//session get
Session.get = function(key) {
        var data = this[key];
        if (!data || data === "null") {
            return null;
        }
        return JSON.parse(data).value;

    }

//session set
Session.set = function(key, value) {
        var data = {
            value: value
        }
        this[key] = JSON.stringify(data);
    }

//session remove
Session.remove = function(key){
            this.removeItem(key);
        }

//common getCurrentTimeStamp
let getCurrentTimeStamp = function() {
        return Date.parse(new Date());
    }

Local.getCurrentTimeStamp = getCurrentTimeStamp;

export default {
  Local,
  Session
}
