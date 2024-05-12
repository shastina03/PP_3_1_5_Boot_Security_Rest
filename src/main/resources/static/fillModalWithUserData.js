async function fillModalWithUserData(form, modal, id){
    modal.show();
    let user = await getUserId(id);
    form.id.value = user.id;
    form.firstName.value = user.firstName;
    form.lastName.value = user.lastName;
    form.age.value = user.age;
    form.username.value = user.username;
    form.roles.value = user.shortRole;
}