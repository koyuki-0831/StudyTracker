function getLoginUserIdOrRedirect() {

    const userId =
        sessionStorage.getItem("loginUserId");

    const loginTime =
        sessionStorage.getItem("loginTime");

    const limitTime =
        24 * 60 * 60 * 1000;

    if(!userId || !loginTime) {
        alert("ログインしてください");
        window.location.href = "/login";
        throw new Error("Not Login");
    }

    if(Date.now() - Number(loginTime) > limitTime) {
        sessionStorage.clear();

        alert("ログイン期限が切れました。再ログインしてください");

        window.location.href = "/login";
        throw new Error("Login Expired");
    }

    return userId;
}