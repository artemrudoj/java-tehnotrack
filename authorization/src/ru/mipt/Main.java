package ru.mipt;


public class Main {

    public static void main(String[] args) throws Exception {


        UserStore userStore = new UserStore();
        AuthorizationService service = new AuthorizationService(userStore);

        service.startAuthorization();


    }
}
