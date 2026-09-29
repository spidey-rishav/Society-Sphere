$mailUsername = [Environment]::GetEnvironmentVariable('MAIL_USERNAME', 'User')
$mailPassword = [Environment]::GetEnvironmentVariable('MAIL_PASSWORD', 'User')
$mailEnabled = [Environment]::GetEnvironmentVariable('APP_MAIL_ENABLED', 'User')

if ([string]::IsNullOrWhiteSpace($mailUsername) -or [string]::IsNullOrWhiteSpace($mailPassword) -or $mailEnabled -ne 'true') {
    Write-Error 'Mail settings are incomplete. Set MAIL_USERNAME, MAIL_PASSWORD, and APP_MAIL_ENABLED=true in Windows User environment variables first.'
    exit 1
}

$env:MAIL_USERNAME = r4rishavmodi@gmail.com
$env:MAIL_PASSWORD = oyyd vyea beaa ivmv
$env:APP_MAIL_ENABLED = true

Write-Host "Starting Society Sphere backend with mail enabled for $mailUsername"
& "$PSScriptRoot\mvnw.cmd" spring-boot:run
