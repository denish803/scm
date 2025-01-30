/* Contact data */
function contactView(id) {

    const URL = "http://localhost:8080/api/contact/profile?id="+id;

    fetch(URL)
    .then(responce => responce.json())
    .then(apiData => {
        document.getElementById("name").innerHTML = apiData.data['name']
        document.getElementById("email").innerHTML = apiData.data['email']
        document.getElementById("phoneNo").innerHTML = apiData.data['phoneNo']
        document.getElementById("address").innerHTML = apiData.data['address']
        let imageURL = apiData.data['image'];
        if (imageURL == null) {
            imageURL = "https://brandeps.com/icon-download/C/Contact-icon-vector-01.svg";
        }
        document.getElementById("image").setAttribute("src", imageURL);
        
        document.getElementById("image").setAttribute("alt", apiData.data['name']);
        document.getElementById("description").innerHTML = apiData.data['description']

        var linkName = null;
        var facebookURL = linkName =  apiData.data['socialLink'][0];
        if (facebookURL == null) {
            facebookURL = "";
            linkName = "No facebook Link Set"
        }               
        document.getElementById("facebookLink").setAttribute('href', facebookURL);
        document.getElementById("facebookLink").setAttribute('target', "_blank"); 
        document.getElementById("facebookLink").innerHTML = linkName;
        var linkdnURL = linkName =  apiData.data['socialLink'][1];
        
        if (linkdnURL == null) {
            linkdnURL = "/user/contact";
            linkName = "No Linkdn Link Set"
        }
        document.getElementById("linkdnLink").innerHTML = linkName; 
        document.getElementById("linkdnLink").setAttribute('href', linkdnURL); 
        document.getElementById("linkdnLink").setAttribute('target', "_blank"); 
        if (apiData.data['favorite']) {
            document.getElementById("favorite").checked = true;
        }
    })
    .catch (error => {
        console.error("Error is : ", error);
    })

}