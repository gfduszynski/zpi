function fillForm(userId){
    $.ajax({
        type: 'GET',
        url: "../UserServlet",
        data: {
            userid:userId
        },
        context: document.body
    }).done(function(data) { 
        fillFormWithData(data);
    });
}

function fillFormWithData(data){
    var user = jQuery.parseJSON(data);
    //alert(user.userClub.clubId);
    $('#tab1Form\\:userIdForm').val(user.userId);
    $('#tab1Form\\:userFirstNameForm').val(user.firstName);
    $('#tab1Form\\:userLastNameForm').val(user.lastName);
    $('#tab1Form\\:userBirthDateForm').val(user.birthDate);
    $('#tab1Form\\:userCreationDateForm').val(user.creationDate);
    $('#tab1Form\\:userEmailForm').val(user.email);
    $('#tab1Form\\:userSexForm').val(user.sex);
    $('#tab1Form\\:userClubIdForm').val(user.userClub.clubId);
}