<#import "template.ftl" as layout>
<#import "components/atoms/button.ftl" as button>
<#import "components/atoms/button-group.ftl" as buttonGroup>
<#import "components/atoms/form.ftl" as form>
<#import "components/atoms/input.ftl" as input>
<#import "components/atoms/link.ftl" as link>

<@layout.registrationLayout
  displayMessage=!messagesPerField.existsError("firstName", "lastName", "email", "username", "password", "password-confirm")
  ;
  section
>
  <#if section="header">
    ${msg("registerTitle")}
  <#elseif section="form">
    <@form.kw action=url.registrationAction method="post">
      <@input.kw
        autocomplete="given-name"
        autofocus=true
        invalid=messagesPerField.existsError("firstName")
        label=msg("firstName")
        message=kcSanitize(messagesPerField.get("firstName"))
        name="firstName"
        type="text"
        value=(register.formData.firstName)!''
      />
      <@input.kw
        autocomplete="family-name"
        invalid=messagesPerField.existsError("lastName")
        label=msg("lastName")
        message=kcSanitize(messagesPerField.get("lastName"))
        name="lastName"
        type="text"
        value=(register.formData.lastName)!''
      />
      <@input.kw
        autocomplete="email"
        invalid=messagesPerField.existsError("email")
        label=msg("email")
        message=kcSanitize(messagesPerField.get("email"))
        name="email"
        type="email"
        value=(register.formData.email)!''
      />
      <#if !realm.registrationEmailAsUsername>
        <@input.kw
          autocomplete="username"
          invalid=messagesPerField.existsError("username")
          label=msg("username")
          message=kcSanitize(messagesPerField.get("username"))
          name="username"
          type="text"
          value=(register.formData.username)!''
        />
      </#if>
      <#if passwordRequired??>
        <@input.kw
          autocomplete="new-password"
          invalid=messagesPerField.existsError("password", "password-confirm")
          label=msg("password")
          message=kcSanitize(messagesPerField.getFirstError("password", "password-confirm"))
          name="password"
          type="password"
        />
        <@input.kw
          autocomplete="new-password"
          invalid=messagesPerField.existsError("password-confirm")
          label=msg("passwordConfirm")
          message=kcSanitize(messagesPerField.get("password-confirm"))
          name="password-confirm"
          type="password"
        />
      </#if>
        <select
          id="user.attributes.locationId"
          value="${(register.formData['user.attributes.locationId']!'')}"
          class="${properties.kcInputClass!}"
          style="width: 100% ; border: 1px solid rgba(0,0,0,0.2) ; border-radius: 5px"
          name="user.attributes.locationId"
        >
          <option value="1">Bizert</option>
          <option value="2">Tunis</option>
          <option value="3">Ben Arous</option>
          <option value="4">Kasserine</option>
          <option value="5">Sidi bouzid</option>
          <option value="6">Zaghouan</option>
          <option value="7">Nabeul</option>
          <option value="8">Sousse</option>
          <option value="9">Beja</option>
          <option value="10">Gabes</option>
          <option value="11">Mednine</option>
          <option value="12">Tataouine</option>
          <option value="13">Kef</option>
          <option value="14">Jandouba</option>
          <option value="15">Ariana</option>
          <option value="16">Siliana</option>
          <option value="17">Sfax</option>
          <option value="18">Gafsa</option>
          <option value="19">Kébili</option>
          <option value="20">Mahdia</option>
          <option value="21">Manouba</option>
          <option value="22">Monastir</option>
        </select>
<#--          <@input.kw-->
<#--            autocomplete="phone"-->
<#--            type="text"-->
<#--            label=msg("phone")-->
<#--            id="user.attributes.phone"-->
<#--            class="${properties.kcInputClass!}"-->
<#--            name="user.attributes.phone"-->
<#--            value="${(register.formData['user.attributes.phone']!'')}"-->
<#--          />-->
      <#if recaptchaRequired??>
        <div class="g-recaptcha" data-sitekey="${recaptchaSiteKey}" data-size="compact"></div>
      </#if>
      <@buttonGroup.kw>
        <@button.kw color="primary" type="submit">
          ${msg("doRegister")}
        </@button.kw>
      </@buttonGroup.kw>
    </@form.kw>
  <#elseif section="nav">
    <@link.kw color="secondary" href=url.loginUrl size="small">
      ${kcSanitize(msg("backToLogin"))?no_esc}
    </@link.kw>
  </#if>
</@layout.registrationLayout>
