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
 })

//
// document.addEventListener("DOMContentLoaded", function(){
//    const checkbox = document.getElementById("newAlbumCheck");
//    const formDiv = document.getElementById("new-album-form");
//
//    formDiv.style.display = "none";
//
//    checkbox.addEventListener("change", function(){
//        if(checkbox.checked){
//                formDiv.style.display = "block";
//            }else{
//                formDiv.style.display = "none";
//            }
//    })
// })