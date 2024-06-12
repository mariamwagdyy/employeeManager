function createForms() {
  var numberOfKnownLanguages = document.getElementById('numberOfKnownLanguages').value;
  var container = document.getElementById('knownLanguagesContainer');
  container.innerHTML = ''; // Clear previous content

  for (var i = 0; i < numberOfKnownLanguages; i++) {
      var languageLabel = document.createElement('label');
      languageLabel.textContent = 'Known Language ' + (i + 1) + ':';

      var languageInput = document.createElement('input');
      languageInput.type = 'text';
      languageInput.name = 'knownLanguage' + (i + 1);
      languageInput.placeholder = 'Enter Known Language';

      var scoreLabel = document.createElement('label');
      scoreLabel.textContent = 'Score out of 100' + (i + 1) + ':';

      var scoreInput = document.createElement('input');
      scoreInput.type = 'text'; // You can change this to 'number' if you want numeric scores
      scoreInput.name = 'score' + (i + 1);
      scoreInput.placeholder = 'Enter Score';

      container.appendChild(languageLabel);
      container.appendChild(languageInput);
      container.appendChild(document.createElement('br'));

      container.appendChild(scoreLabel);
      container.appendChild(scoreInput);
      container.appendChild(document.createElement('br')); // Line break for spacing
  }
}

// function submitForm() {
//   var numberOFStudents = document.getElementById("numberOfStudents").value;
//   response = [];

//   for (i = 1; i <= numberOFStudents; i++) {
//     // Get form data
//     var id = document.getElementById("id" + i).value;
//     var firstName = document.getElementById("firstName" + i).value;
//     var lastName = document.getElementById("lastName" + i).value;
//     var gender = document.getElementById("gender" + i).value;
//     var gpa = document.getElementById("gpa" + i).value;
//     var level = document.getElementById("level" + i).value;
//     var address = document.getElementById("address" + i).value;

//     // Create a JSON object with the form data
//     var formData = {
//       ID: id,
//       FirstName: firstName,
//       LastName: lastName,
//       Gender: gender,
//       GPA: gpa,
//       Level: level,
//       Address: address,
//     };

//     response.push(formData);
//   }

//   $.ajax({
//     url: "/submit-form-data",
//     method: "POST",
//     contentType: "application/json;charset=UTF-8",
//     data: JSON.stringify(response),
//     dataType: "html", // Specify that you expect HTML as the response
//     success: function (response) {
//       // Replace the entire body content with the received HTML
//       $("body").html(response);
//     },
//     error: function (xhr, status, error) {
//       // Handle error cases if needed
//       console.error("Error during API request:", status, error);
//     },
//   });
//}
