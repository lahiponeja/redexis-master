
<#-- ---------- Call Center Phone ---------- -->
<#-- We use slicing to split the phone and the prefix if it's needed -->

<#assign call_center_phone = themeDisplay.getThemeSetting("Teléfono de Call Center") />
<#assign call_center_phone_prefix = "+34" />

<#if call_center_phone?starts_with("+") />
  <#assign call_center_phone_prefix = call_center_phone[0..*3] />
  <#assign call_center_phone = call_center_phone[3..] />
</#if>
