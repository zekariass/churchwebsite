
const editor = Jodit.make("#editor", {
            "autofocus": true,
            "iframe": true,
            "uploader": {
              "insertImageAsBase64URI": true
            },
            //"readonly": true,
            "spellcheck": true,
            //"theme": "dark",
            "enter": "P",
            "defaultMode": "1",
            "saveHeightInStorage": true,
            "saveModeInStorage": true,
            "minHeight": 300,
            "iframeCSSLinks": [],
            "iframeBaseUrl": "http://",
            "allowDragAndDrop": true,
            //"buttons": "bold,italic,underline,strikethrough,eraser,ul,ol,font,fontsize,paragraph,lineHeight,superscript,subscript,classSpan,file,image,video,spellcheck,speechRecognize"


    });