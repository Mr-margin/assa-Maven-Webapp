/*!
 * Distpicker v1.0.2
 * https://github.com/tshi0912/city-picker
 *
 * Copyright (c) 2014-2016 Tao Shi
 * Released under the MIT license
 *
 * Date: 2016-02-29T12:11:36.473Z
 */

(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define('ChineseDistricts', [], factory);
    } else {
        factory();
    }
})(function () {

    var ChineseDistricts = {
            86: {
                '': [{code: '150100', address: '呼和浩特市'},
                     {code: '150200', address: '包头市'},
                     {code: '150300', address: '乌海市'},
                     {code: '150400', address: '赤峰市'},
                     {code: '150500', address: '通辽市'},
                     {code: '150600', address: '鄂尔多斯市'},
                     {code: '150700', address: '呼伦贝尔市'},
                     {code: '150800', address: '巴彦淖尔市'},
                     {code: '150900', address: '乌兰察布市'},
                     {code: '151000', address: '兴安盟'},
                     {code: '151100', address: '锡林郭勒盟'},
                     {code: '151200', address: '阿拉善盟'}]
            },
            150100: {
                150102: '新城区',
                150103: '回民区',
                150104: '玉泉区',
                150105: '赛罕区',
                150121: '土默特左旗',
                150122: '托克托县',
                150123: '和林格尔县',
                150124: '清水河县',
                150125: '武川县'
            },
            150200: {
                150202: '东河区',
                150203: '昆都仑区',
                150204: '青山区',
                150205: '石拐区',
                150206: '白云鄂博矿区',
                150207: '九原区',
                150221: '土默特右旗',
                150222: '固阳县',
                150223: '达尔罕茂明安联合旗'
            },
            150300: {
                150302: '海勃湾区',
                150303: '海南区',
                150304: '乌达区'
            },
            150400: {
                150402: '红山区',
                150403: '元宝山区',
                150404: '松山区',
                150421: '阿鲁科尔沁旗',
                150422: '巴林左旗',
                150423: '巴林右旗',
                150424: '林西县',
                150425: '克什克腾旗',
                150426: '翁牛特旗',
                150428: '喀喇沁旗',
                150429: '宁城县',
                150430: '敖汉旗'
            },
            150500: {
                150502: '科尔沁区',
                150521: '科尔沁左翼中旗',
                150522: '科尔沁左翼后旗',
                150523: '开鲁县',
                150524: '库伦旗',
                150525: '奈曼旗',
                150526: '扎鲁特旗',
                150581: '霍林郭勒市'
            },
            150600: {
                150602: '东胜区',
                150621: '达拉特旗',
                150622: '准格尔旗',
                150623: '鄂托克前旗',
                150624: '鄂托克旗',
                150625: '杭锦旗',
                150626: '乌审旗',
                150627: '伊金霍洛旗'
            },
            150700: {
                150702: '海拉尔区',
                150703: '扎赉诺尔区',
                150721: '阿荣旗',
                150722: '莫力达瓦达斡尔族自治旗',
                150723: '鄂伦春自治旗',
                150724: '鄂温克族自治旗',
                150725: '陈巴尔虎旗',
                150726: '新巴尔虎左旗',
                150727: '新巴尔虎右旗',
                150781: '满洲里市',
                150782: '牙克石市',
                150783: '扎兰屯市',
                150784: '额尔古纳市',
                150785: '根河市'
            },
            150800: {
                150802: '临河区',
                150821: '五原县',
                150822: '磴口县',
                150823: '乌拉特前旗',
                150824: '乌拉特中旗',
                150825: '乌拉特后旗',
                150826: '杭锦后旗'
            },
            150900: {
                150902: '集宁区',
                150921: '卓资县',
                150922: '化德县',
                150923: '商都县',
                150924: '兴和县',
                150925: '凉城县',
                150926: '察哈尔右翼前旗',
                150927: '察哈尔右翼中旗',
                150928: '察哈尔右翼后旗',
                150929: '四子王旗',
                150981: '丰镇市'
            },
            152200: {
                152201: '乌兰浩特市',
                152202: '阿尔山市',
                152221: '科尔沁右翼前旗',
                152222: '科尔沁右翼中旗',
                152223: '扎赉特旗',
                152224: '突泉县'
            },
            152500: {
                152501: '二连浩特市',
                152502: '锡林浩特市',
                152522: '阿巴嘎旗',
                152523: '苏尼特左旗',
                152524: '苏尼特右旗',
                152525: '东乌珠穆沁旗',
                152526: '西乌珠穆沁旗',
                152527: '太仆寺旗',
                152528: '镶黄旗',
                152529: '正镶白旗',
                152530: '正蓝旗',
                152531: '多伦县'
            },
            152900: {
                152921: '阿拉善左旗',
                152922: '阿拉善右旗',
                152923: '额济纳旗'
            },
        }
        ;

    if (typeof window !== 'undefined') {
        window.ChineseDistricts = ChineseDistricts;
    }

    return ChineseDistricts;

});
