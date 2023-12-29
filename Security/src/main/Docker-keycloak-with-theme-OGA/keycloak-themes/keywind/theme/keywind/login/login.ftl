<#import "template.ftl" as layout>
<#import "components/atoms/button.ftl" as button>
<#import "components/atoms/button-group.ftl" as buttonGroup>
<#import "components/atoms/checkbox.ftl" as checkbox>
<#import "components/atoms/form.ftl" as form>
<#import "components/atoms/input.ftl" as input>
<#import "components/atoms/link.ftl" as link>
<#import "components/molecules/identity-provider.ftl" as identityProvider>
<#import "features/labels/username.ftl" as usernameLabel>
<@layout.registrationLayout
  displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??
  displayMessage=!messagesPerField.existsError("username", "password")
  ;
  section
>
  <#if section="header">
  <#elseif section="form">
    <#if realm.password>
      <@form.kw
        action=url.loginAction
        method="post"
        onsubmit="login.disabled = true; return true;"
      >
        <div class=" w-full flex items-center justify-center">
         <img src="https://i.imgur.com/wtla6VI.png" alt="login" style="width:auto; height:70px">
        </div>
        <hr style="width: 100%; border: none; border-top: 3px solid #ff8079; margin: 16px 0;">

        <p style="font-size: xx-large; font-weight: 900 ; color: #344056">Sign in</p>
        <input
          name="credentialId"
          type="hidden"
          value="<#if auth.selectedCredential?has_content>${auth.selectedCredential}</#if>"
        >

        <div style="margin-bottom: 16px;">
          <label style="display: block; font-weight: bold; font-size: 12px;color: gray">Username</label>
            <@input.kw
            autocomplete=realm.loginWithEmailAllowed?string("email", "username")
            autofocus=true
            disabled=usernameEditDisabled??
            invalid=messagesPerField.existsError("username", "password")
            label=""
            message=kcSanitize(messagesPerField.getFirstError("username", "password"))
            name="username"
            type="text"
            style="height: 50px; border: 3px solid light-gray; border-radius: 5px; padding: 10px;"
            value=(login.username)!''
            />
        </div>
        <div style="margin-bottom: 16px;">
          <label style="display: block; font-weight: bold; font-size: 12px; color: gray">Password</label>
            <div style="position: relative">
            <@input.kw
            invalid=messagesPerField.existsError("username", "password")
            label=""
            name="password"
            type="password"
            style="height: 50px; border: 3px solid light-gray; border-radius: 5px; padding: 10px;"
            >

            </@input.kw>
              <button type="button" onclick="togglePasswordVisibility()" style="position: absolute; top:30%; right: 10px ">
                <svg id="eye-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20"><g fill="#c1c1c1"><path fill-rule="evenodd" d="M3.707 2.293a1 1 0 0 0-1.414 1.414l14 14a1 1 0 0 0 1.414-1.414l-1.473-1.473A10.014 10.014 0 0 0 19.542 10C18.268 5.943 14.478 3 10 3a9.958 9.958 0 0 0-4.512 1.074l-1.78-1.781Zm4.261 4.26l1.514 1.515a2.003 2.003 0 0 1 2.45 2.45l1.514 1.514a4 4 0 0 0-5.478-5.478Z" clip-rule="evenodd"/><path d="M12.454 16.697L9.75 13.992a4 4 0 0 1-3.742-3.741L2.335 6.578A9.98 9.98 0 0 0 .458 10c1.274 4.057 5.065 7 9.542 7c.847 0 1.669-.105 2.454-.303Z"/></g></svg>
              </button>
            </div>


          <script>
            function togglePasswordVisibility() {
              const eyeIcon = document.querySelector('#eye-icon');
              const passwordInput = document.querySelector('input[name="password"]');

              if (passwordInput.type === "password") {
                passwordInput.type = "text";
                eyeIcon.innerHTML = '<g fill="#c1c1c1"><path d="M10 12a2 2 0 1 0 0-4a2 2 0 0 0 0 4Z"/><path fill-rule="evenodd" d="M.458 10C1.732 5.943 5.522 3 10 3s8.268 2.943 9.542 7c-1.274 4.057-5.064 7-9.542 7S1.732 14.057.458 10ZM14 10a4 4 0 1 1-8 0a4 4 0 0 1 8 0Z" clip-rule="evenodd"/></g>';
              } else {
                passwordInput.type = "password";
                eyeIcon.innerHTML = '<g fill="#c1c1c1"><path fill-rule="evenodd" d="M3.707 2.293a1 1 0 0 0-1.414 1.414l14 14a1 1 0 0 0 1.414-1.414l-1.473-1.473A10.014 10.014 0 0 0 19.542 10C18.268 5.943 14.478 3 10 3a9.958 9.958 0 0 0-4.512 1.074l-1.78-1.781Zm4.261 4.26l1.514 1.515a2.003 2.003 0 0 1 2.45 2.45l1.514 1.514a4 4 0 0 0-5.478-5.478Z" clip-rule="evenodd"/><path d="M12.454 16.697L9.75 13.992a4 4 0 0 1-3.742-3.741L2.335 6.578A9.98 9.98 0 0 0 .458 10c1.274 4.057 5.065 7 9.542 7c.847 0 1.669-.105 2.454-.303Z"/></g>';
              }
            }
          </script>
        </div>



        <#if realm.rememberMe && !usernameEditDisabled?? || realm.resetPasswordAllowed>
          <div class="flex items-center justify-between">
            <#if realm.rememberMe && !usernameEditDisabled??>
              <@checkbox.kw
                checked=login.rememberMe??
                label=msg("rememberMe")
                name="rememberMe"
              />
            </#if>
            <#if realm.resetPasswordAllowed>
              <@link.kw color="primary" href=url.loginResetCredentialsUrl size="small">
                ${msg("doForgotPassword")}
              </@link.kw>
            </#if>
          </div>
        </#if>
        <@buttonGroup.kw>
          <@button.kw name="login" type="submit" style="border-radius: 500px; background:#6a00e4">
            ${msg("doLogIn")}
          </@button.kw>
        </@buttonGroup.kw>
      </@form.kw>
    </#if>
    <#if realm.password && social.providers??>
      <@identityProvider.kw providers=social.providers />
    </#if>
  <#elseif section="info">
    <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
      <div class="text-center">
        ${msg("noAccount")}
        <@link.kw color="primary" href=url.registrationUrl>
          ${msg("doRegister")}
        </@link.kw>
      </div>
    </#if>

  </#if>
</@layout.registrationLayout>
<div class="flex-col justify-center items-center text-center" style="width: 30% ">
  <hr style="width:100%; border: none; border-top: 3px solid #004158; justify-self: center; ">
  <div class="flex justify-around items-center mt-2">
  </div>
  <p style="font-size: 12px">© 2023 - 2024 - <a href="https://onegateafrica.com/" style="color: #2563eb" >One Gate Africa</a> - all Rights Reserved.</p>
  <p style="font-size: 12px">- Version 1.0.111-SNAPSHOT</p>
</div>

