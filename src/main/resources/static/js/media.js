 $(document).ready(function(){
    const el = $('#new-album-form');
    let savedEl = el.clone();
    el.remove();
    $('#newAlbumCheck').change(function(){
        if($(this).is(':checked')){
            let savedEl2 = savedEl;
            $('#newAlbumDiv').append(savedEl2);
            $('#existingAlbums').val($('#existingAlbums option:first').val())
            savedEl2.show();
            $('#existingAlbums').prop('disabled', true);
        }else{
            let savedEl3 = $('#new-album-form');;
            $('#existingAlbums').prop('disabled', false);
            savedEl3.remove();
        }

    })


    // Attachment

    $('#attachmentType').change(function(event){
        var selectedText = $("#attachmentType option:selected").text()

        const fileInputId = $('#attachmentPath');

        if(selectedText.toLowerCase() == "word"){
            fileInputId.attr('accept', '.doc,.docx')
        }else if(selectedText.toLowerCase() == "pdf"){
            fileInputId.attr('accept', '.pdf')
        }else{
            fileInputId.attr('accept', '.*')
        }

    })

    $('#attachmentType').trigger('change');

 })


 $('.carousel-indicators').on('click', function (event) {
    event.stopPropagation();
    event.preventDefault();
});

$('.carousel-control-prev, .carousel-control-next').on('click', function(){
    event.stopPropagation();
    event.preventDefault();
})

//function copyToClipboard(button) {
//      // Get the hidden text to copy
//      const hiddenText = button.getAttribute('data-copy');
//      let urlToCopy = window.location.origin + hiddenText;
//
//      urlToCopy = urlToCopy.replace(/\\/g, '/');
//
//
//      // Copy the hidden text to the clipboard
//      navigator.clipboard.writeText(urlToCopy).then(() => {
////        alert('Copied to clipboard: ' + urlToCopy);
//            button.hide();
////            $(".path-copied").show();
//          setTimeout(()=>{
////            $(".path-copied").hide();
////            $(".path-btn").show();
//          }, 3000)
//      }).catch(err => {
//        console.error('Failed to copy: ', err);
//      });
//    }


function copyToClipboard(button) {
      // Get the value from the data-copy attribute

       const hiddenText = button.getAttribute('data-copy');
       let pathToCopy;

       if(hiddenText.toLowerCase().includes('http')){
           pathToCopy = hiddenText;
       }else{
            pathToCopy = window.location.origin + hiddenText;
       }



        pathToCopy = pathToCopy.replace(/\\/g, '/');

      // Create a temporary input element to hold the value to be copied
      const input = document.createElement('input');
      input.value = pathToCopy;
      document.body.appendChild(input);

      // Select the text inside the input
      input.select();
      input.setSelectionRange(0, 99999); // For mobile devices

      // Copy the selected text to the clipboard
      document.execCommand('copy');

      // Remove the temporary input element
      document.body.removeChild(input);

      // Show the success message
      const successMessage = button.nextElementSibling; // next <p> element
      successMessage.style.display = 'block';

  // Hide the success message after 3 seconds
  setTimeout(function() {
    successMessage.style.display = 'none';
  }, 3000); // Hide after 3 seconds
}


 function sortByCriteria(el){
         // Sort attachment list
          const sortSelect = $(".sort-by");
          const sortSelectText = $(".sort-by option:selected").val(); // Get the value of the selected option
          const navigateTo = sortSelect.data("navigate-to"); // Access the `data-navigate-to` attribute properly
          window.location = window.location.origin + navigateTo + "?sortBy=" + encodeURIComponent(sortSelectText);
     }


