const apiUrl = 'http://localhost:8080/upload';

const token = getCookie();

$('input[type="file"]').change(function(e){
    var fileName = e.target.files[0].name;
    $('.custom-file-label').html(fileName);
});

document.getElementById('upload-button').addEventListener('click', () => {
	const input = document.getElementById('media-input');
	if (input.files.length === 0) {
        $('#exampleModal').modal('show');
		return;
	}

	const file = input.files[0];
	const formData = new FormData();
	formData.append('file', file);

	const request = new XMLHttpRequest();
	request.open('POST', apiUrl);
	request.setRequestHeader('Authorization', `Bearer ${token ? token : ""}`);
	request.onload = () => {
        defaultButtonState();
        setResponseToHTML(request);
    };
	request.send(formData);
    changeButtonState();    
});

function setResponseToHTML(request) {
    if (request.status == 201) {
        const jsonResponse = JSON.parse(request.responseText);

        document.getElementById("nome").innerHTML = jsonResponse.fullName;
        document.getElementById("identidade").innerHTML = jsonResponse.identityNumber;
        document.getElementById("nascimento").innerHTML = jsonResponse.birthDate;

        document.getElementsByClassName('card')[0].style.visibility = 'visible';
    }
}

function changeButtonState(){
    const button = document.getElementById('upload-button');

    button.textContent = "Enviado. Aguarde";
    button.style.backgroundColor = "#737475";
}

function defaultButtonState(){
    const button = document.getElementById('upload-button');

    button.textContent = "Enviar";
    button.style.backgroundColor = "#4CAF50";
}

function getCookie() {
    var cookies = document.cookie.split(';');
    for(var i = 0; i < cookies.length; i++) {
      var cookie = cookies[i].trim();
      if(cookie.indexOf('.accessToken') !== -1) {
        return cookie;
      }
    }
    return null;
}