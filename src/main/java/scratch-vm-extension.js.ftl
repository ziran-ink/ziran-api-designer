const ArgumentType = require('../../extension-support/argument-type');
const BlockType = require('../../extension-support/block-type');
const formatMessage = require('format-message');
const nets = require('nets');

const iconURI = 'data:image/png;base64,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';

const webApiIconUri = 'data:image/png;base64,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';

const serverTimeoutMs = 10000; // 10 seconds (chosen arbitrarily).

class Scratch3ZiranBlocks {
    constructor (runtime) {
        this.runtime = runtime;
    }

    getInfo () {
        return {
            id: 'ziran',
            name: '自然π',
            blockIconURI: iconURI,
            blocks: [
                <#list apiDefinitions as apiDefinition>
                    <#if apiDefinition.requestDefinition?? && apiDefinition.requestDefinition.fieldDefinitions??>
                    {
                        opcode: 'setRequestParam__${apiDefinition.name?replace(".", "__")}',
                        text: formatMessage('设置【${apiDefinition.comment}】的请求参数[PARAM_NAME]值为[PARAM_VALUE]'),
                        blockType: BlockType.COMMAND,
                        arguments: {
                            PARAM_NAME: {
                                type: ArgumentType.STRING,
                                menu: 'requestParamNames__${apiDefinition.name?replace(".", "__")}'
                            },
                            PARAM_VALUE: {
                                type: ArgumentType.STRING
                            }
                        },
                        blockIconURI: <#if apiDefinition.ext?? && apiDefinition.ext.icon??>'${apiDefinition.ext.icon}'<#else>webApiIconUri</#if>
                    },
                    </#if>
                {
                    opcode: 'isSendRequestSuccess__${apiDefinition.name?replace(".", "__")}',
                    text: '发送【${apiDefinition.comment}】请求成功？',
                    blockType: BlockType.BOOLEAN,
                    blockIconURI: <#if apiDefinition.ext?? && apiDefinition.ext.icon??>'${apiDefinition.ext.icon}'<#else>webApiIconUri</#if>
                },
                {
                    opcode: 'getResponseValue__${apiDefinition.name?replace(".", "__")}',
                    text: formatMessage('【${apiDefinition.comment}】的响应的[FIELD_NAME]值'),
                    blockType: BlockType.REPORTER,
                    arguments: {
                        FIELD_NAME: {
                            type: ArgumentType.STRING,
                            menu: 'responseFieldNames__${apiDefinition.name?replace(".", "__")}',
                            defaultValue: '_all'
                        }
                    },
                    blockIconURI: <#if apiDefinition.ext?? && apiDefinition.ext.icon??>'${apiDefinition.ext.icon}'<#else>webApiIconUri</#if>
                },
                '---',
                </#list>
            ],
            menus: {
                <#list apiDefinitions as apiDefinition>
                    <#if apiDefinition.requestDefinition?? && apiDefinition.requestDefinition.fieldDefinitions??>
                        'requestParamNames__${apiDefinition.name?replace(".", "__")}': [
                            <#list apiDefinition.requestDefinition.fieldDefinitions as fieldDefinition>
                            {text: "${fieldDefinition.comment?replace("\"", "\\\"")}", value: "${fieldDefinition.name}"},
                            </#list>
                        ],
                    </#if>
                    'responseFieldNames__${apiDefinition.name?replace(".", "__")}': [
                        {text: "*全部", value: "_all"},
                        <#if apiDefinition.responseDefinition??>
                            <#list apiDefinition.responseDefinition.fieldDefinitions as fieldDefinition>
                            {text: "${fieldDefinition.comment}", value: "${fieldDefinition.name}"},
                            </#list>
                        </#if>
                    ],
                </#list>
            }
        };
    }

    <#list apiDefinitions as apiDefinition>

    // ${apiDefinition.comment}

    setRequestParam__${apiDefinition.name?replace(".", "__")} (args, util) {
        if (args.PARAM_NAME == "") {
            return;
        }
        if (!util.thread._x_request__${apiDefinition.name?replace(".", "__")}) {
            util.thread._x_request__${apiDefinition.name?replace(".", "__")} = {
                apiName: "${apiDefinition.name}"
            };
        }
        util.thread._x_request__${apiDefinition.name?replace(".", "__")}[args.PARAM_NAME] = args.PARAM_VALUE;
    }

    isSendRequestSuccess__${apiDefinition.name?replace(".", "__")} (args, util) {
        return new Promise(resolve => {
            if (!util.thread._x_request__${apiDefinition.name?replace(".", "__")}) {
                util.thread._x_request__${apiDefinition.name?replace(".", "__")} = {
                    apiName: "${apiDefinition.name}"
                };
            }
            var request = util.thread._x_request__${apiDefinition.name?replace(".", "__")};
            nets({
                body: JSON.stringify(request),
                url: 'https://${serverAddressDefinition.host}:${serverAddressDefinition.port?c}${serverAddressDefinition.url}',
                timeout: serverTimeoutMs,
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                }
            }, (err, resp, body) => {
                if (err) {
                    resolve({code:12, message:"无法连接服务器"});
                } else {
                    resolve(JSON.parse(body));
                }
            });
        }).then(response => {
            response._rawRequest = util.thread._x_request__${apiDefinition.name?replace(".", "__")};
            util.thread._x_response__${apiDefinition.name?replace(".", "__")} = response;
            return response.code == 0;
        });
    }

    getResponseValue__${apiDefinition.name?replace(".", "__")} (args, util) {
        var resp = util.thread._x_response__${apiDefinition.name?replace(".", "__")};
        if (resp) {
            if (args.FIELD_NAME == '_all') {
                return JSON.stringify(resp);
            } else {
                return JSON.stringify(resp[args.FIELD_NAME]);
            }
        }
    }
    </#list>
}

module.exports = Scratch3ZiranBlocks;
