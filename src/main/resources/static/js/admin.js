console.log("Admin Js Work");

document.querySelector("#image_file").addEventListener("change", function (event) {
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = function () {
        document.querySelector("#image_file_upload").setAttribute("src", reader.result)
        .setAttribute("alt", reader.readAsText);
    } 
    reader.readAsDataURL(file);
})