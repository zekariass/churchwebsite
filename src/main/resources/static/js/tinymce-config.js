tinymce.init({
    selector: 'textarea#tiny',
    plugins: [
        // Core editing features
        'anchor', 'autolink', 'charmap', 'codesample', 'emoticons', 'image', 'link', 'lists', 'media', 'searchreplace', 'table', 'visualblocks', 'wordcount', 'code',

        // Optional premium features (commented out)
        // 'checklist', 'mediaembed', 'casechange', 'export', 'formatpainter', 'pageembed', 'a11ychecker', 'tinymcespellchecker', 'permanentpen', 'powerpaste', 'advtable', 'advcode', 'editimage', 'advtemplate', 'ai', 'mentions', 'tinycomments', 'tableofcontents', 'footnotes', 'mergetags', 'autocorrect', 'typography', 'inlinecss', 'markdown',
        // 'importword', 'exportword', 'exportpdf'
    ],
    table_responsive_width: true,
    toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table mergetags | addcomment showcomments | spellcheckdialog a11ycheck typography | align lineheight | checklist numlist bullist indent outdent | emoticons charmap | removeformat code',
    tinycomments_mode: 'embedded',
    tinycomments_author: 'Author name',
    relative_urls: false,
    mergetags_list: [
        { value: 'First.Name', title: 'First Name' },
        { value: 'Email', title: 'Email' },
    ],
    ai_request: (request, respondWith) => respondWith.string(() => Promise.reject('See docs to implement AI Assistant')),

    // Add setup function for auto-responsive tables
    setup: (editor) => {
        // Automatically wrap tables in a responsive div on content load
        editor.on('SetContent', () => {
            const tables = editor.getBody().querySelectorAll('table');
            tables.forEach((table) => {
                if (!table.parentElement.classList.contains('table-responsive')) {
                    const wrapper = document.createElement('div');
                    wrapper.className = 'table-responsive';
                    table.parentElement.insertBefore(wrapper, table);
                    wrapper.appendChild(table);
                }
            });
        });

        // Ensure responsiveness for new or modified tables
        editor.on('Change', () => {
            const tables = editor.getBody().querySelectorAll('table');
            tables.forEach((table) => {
                if (!table.parentElement.classList.contains('table-responsive')) {
                    const wrapper = document.createElement('div');
                    wrapper.className = 'table-responsive';
                    table.parentElement.insertBefore(wrapper, table);
                    wrapper.appendChild(table);
                }
            });
        });
    },

    // Inline CSS for responsive tables
    content_style: `
        .table-responsive {
            display: block;
            width: 100%;
            overflow-x: auto;
            -webkit-overflow-scrolling: touch; /* Smooth scrolling for iOS */
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
    `
});
