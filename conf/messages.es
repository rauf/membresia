##### Global #####
app.global.title = Membresia
app.global.menu.title = navegación principal

##### Cuadro Perfil de usuario #####
app.home.welcome = Bienvenido de nuevo
app.menu.logout = cierre de sesión
app.menu.profile = perfil

##### Navegación #####
app.menu.adminUser.title = admin Usuarios
app.menu.adminUser.list = Listar todos los usuarios administradores
app.menu.adminUser.create = Crear un nuevo usuario administrador

app.menu.member.title = Miembros
app.menu.member.list = Listar todos los miembros
app.menu.member.create = Crear un nuevo miembro

app.menu.subscription.title = Suscripciones
app.menu.subscription.list = Enumerar todas las suscripciones
app.menu.subscription.create = Crear una nueva suscripción

app.menu.mailMessage.title = Mensajería
app.menu.mailMessage.send = Redactar un nuevo mensaje
app.menu.messageTemplate.list = Lista todas las plantillas de mensajes
app.menu.messageTemplate.create = Crear una plantilla de mensaje

app.menu.dashboard.title = Dashboard


##### ##### Forma global
app.global.validation.message = Por favor, corrija los errores anteriormente.
app.global.form.submit = Guardar
app.global.form.send = Enviar
app.global.form.pay = Pago
app.global.form.cancel = Cancelar
app.common.form.select.default = Por favor, seleccione un valor
app.common.form.select.unselect = valor No seleccionado


##### Miembros Formulario #####

# Lista
member.list.global.title = Lista de Miembros
member.list.search.label = Búsqueda por
member.list.search.placeholder = Búsqueda por ...
member.list.search.button = Buscar
member.list.table.name = Nombre
member.list.table.phone = Teléfono del contacto
member.list.table.email = Email
member.list.table.city = Ciudad (Estado)
member.list.empty.link = Haga clic aquí para crear un nuevo miembro
member.list.empty.text = No hay miembros disponibles para ser mostrado.
member.list.empty.title = No hay miembros disponibles


# Títulos
member.form.global.new.title = Crear un nuevo miembro
member.form.global.edit.title = Editar miembro
member.list.show.title = perfil de miembro
# Validación
member.form.validation.name = No se dio nombre.
member.form.validation.lastName = No se dio ninguna apellido.
member.form.validation.nif = No se dio ninguna nif.
member.form.validation.email = n de correo electrónico se le dio o el formato no es válido.
member.form.validation.emailUsed = Ya hay un miembro con esta dirección de correo electrónico.
member.form.validation.address = No se dio ninguna dirección.
member.form.validation.city = No se dio ninguna ciudad.
member.form.validation.state = No se dio ninguna estado / provincia.
member.form.validation.country = No se dio país.
member.form.validation.cp = No se dio ninguna código postal.
member.form.validation.phone = No se dio ninguna teléfono.
member.form.validation.subscriptions =


# Validación
member.form.label.name = Nombre
member.form.label.lastName = Apellidos
member.form.label.nif = Número de identificación
member.form.label.email = Email
member.form.label.address = Dirección
member.form.label.city = Ciudad
member.form.label.state = Estado / Provincia
member.form.label.cp = Código Postal
member.form.label.country = País
member.form.label.phone = Teléfono
member.form.label.subscriptions = Suscripciones

# Placeholder
member.form.placeholder.name =
member.form.placeholder.lastName =
member.form.placeholder.nif =
member.form.placeholder.email =
member.form.placeholder.address =
member.form.placeholder.city =
member.form.placeholder.state =
member.form.placeholder.cp =
member.form.placeholder.country =
member.form.placeholder.phone =
member.form.placeholder.subscriptions =

# Ayudantes
member.form.helper.name = Por favor, introduzca el nombre del miembro
member.form.helper.lastName = Por favor, introduzca el apellido miembro
member.form.helper.nif = Por favor, introduzca el nif miembro
member.form.helper.email = Por favor, introduzca el email miembro
member.form.helper.address = Por favor, introduzca la dirección física
member.form.helper.city = Por favor ingrese la ciudad miembro
member.form.helper.state = Por favor, introduzca el Estado miembro o provincia de residencia
member.form.helper.country = Por favor, introduzca el país miembro de la residencia
member.form.helper.cp = Por favor, introduzca el código postal dirección de miembro
member.form.helper.phone = Por favor, ingrese un número de teléfono de contacto
member.form.helper.subscriptions = Por favor, seleccione las suscripciones deseadas

# Mensajes
member.form.save.message.notification = El elemento {0} ha sido creado / modificado correctamente.
member.form.remove.message.success = El elemento seleccionado se ha eliminado.
member.form.remove.message.error = El elemento seleccionado no podría haberme retirado.
member.form.remove.archive.success = El elemento seleccionado se ha archivado.
member.form.remove.archive.error = El elemento seleccionado no podía ser archivado.

member.mail.subject.newAccount = nueva información de la cuenta
member.mail.body.line1 = Hi there.
member.mail.body.line2 = Ha sido registrado como miembro al Membresia.
member.mail.body.line3 = Usted va a recibir correos periódicos con instrucciones sobre cómo hacer sus pagos a plazos en línea.
member.mail.body.line4 = Gracias, tener un día precioso.

# Mostrar
member.show.payment.action = Realizar un pago
member.show.payment.history = Historial de pagos
member.show.payment.empty = No hay pagos registrados encontrados
member.show.payment.text = No hay pagos registrered de este usuario.
member.show.payments.link = Haga clic aquí para realizar un pago.

# Paga
member.pay.global.title = Realizar Pago

member.archive.name.placeholder = MIEMBROS
member.archive.lastName.placeholder = RETIRADO

member.delete.confirm.title = Irreversible Supresión miembro
member.delete.confirm.body = ¿Realmente desea eliminar
member.delete.confirm.close = Cerrar
member.delete.confirm.delete = miembro Delete
##### Usuarios administradores Formulario

# Lista
adminUser.list.global.title = Listado de usuarios admin
adminUser.list.table.name = Nombre
adminUser.list.table.email = Email
adminUser.list.empty.link = Haga clic aquí para crear un nuevo usuario administrador
adminUser.list.empty.text = No hay usuarios administradores disponibles para ser mostrado.
adminUser.list.empty.title = No hay usuarios administradores disponibles

# Títulos
adminUser.form.global.new.title = Crear un nuevo usuario
adminUser.form.global.edit.title = Editar usuario

# Etiquetas
adminUser.form.label.name = Nombre
adminUser.form.label.lastName = Apellido
adminUser.form.label.email = Email
adminUser.form.label.password = Contraseña

# Ayudantes
adminUser.form.helper.name = Por favor, introduzca el nombre de usuario
adminUser.form.helper.lastName = Por favor, introduzca el último nombre de usuario
adminUser.form.helper.password = Por favor, introduzca la contraseña de usuario
adminUser.form.helper.email = Por favor introduzca el correo electrónico del usuario

# Placeholder
adminUser.form.placeholder.name =
adminUser.form.placeholder.lastName =
adminUser.form.placeholder.password =
adminUser.form.placeholder.email =

# Validación
adminUser.form.validation.name = No se dio nombre.
adminUser.form.validation.lastName = No se dio ninguna apellido.
adminUser.form.validation.password = No se dio ninguna contraseña.
adminUser.form.validation.password.length = Dada contraseña es menos de cinco caracteres.
adminUser.form.validation.email = n de correo electrónico se le dio o el formato no es válido.
adminUser.form.validation.emailUsed = Ya existe un usuario con este e-mail.


# Mensajes
adminUser.form.save.message.notification = El usuario "{0}" ha sido creado / modificado correctamente.
adminUser.form.remove.message.success = El usuario seleccionado se ha eliminado.
adminUser.form.remove.message.error = El usuario seleccionado no podía me eliminado.

adminUser.mail.subject.newAccount = nueva información de la cuenta
adminUser.mail.body.line1 = Hi there.
adminUser.mail.body.line2 = Ha sido registrado como usuario administrador en Membresia.
adminUser.mail.body.line3a = Usted nombre de usuario es
adminUser.mail.body.line3b = y su contraseña actual es
adminUser.mail.body.line4 = Por favor, recuerde que usted puede cambiar su nombre de usuario / dirección de correo electrónico y contraseña en cualquier momento desde la configuración del perfil de usuario.
adminUser.mail.body.line5 = Gracias, tiene un día precioso.

adminUser.delete.confirm.title = admin Irreversible Supresión del usuario
adminUser.delete.confirm.body = ¿Realmente desea eliminar
adminUser.delete.confirm.close = Cerrar
adminUser.delete.confirm.delete = Eliminar usuario


##### Plantillas MailMessage #####

# Lista
#messageTemplate.list.global.title = Plantillas de Mensaje
messageTemplate.list.table.title = Título
messageTemplate.list.empty.link = Haga clic aquí para crear una nueva plantilla
messageTemplate.list.empty.text = No hay plantillas disponibles para ser mostrado.
messageTemplate.list.empty.title = No hay plantillas disponibles

# Títulos
messageTemplate.form.global.new.title = Crear una nueva plantilla
messageTemplate.form.global.edit.title = Editar plantilla

# Etiquetas
messageTemplate.form.label.title = Título
messageTemplate.form.label.body = Cuerpo

# Ayudantes
messageTemplate.form.helper.title = Por favor, introduzca el título de la plantilla
messageTemplate.form.helper.body = Por favor, introduzca el cuerpo de la plantilla

# Placeholder
messageTemplate.form.placeholder.title =
messageTemplate.form.placeholder.body =

# Validación
messageTemplate.form.validation.title = No se dio título.
messageTemplate.form.validation.body = No se dio cuerpo.

# Mensajes
messageTemplate.form.save.message.notification = La plantilla "{0}" ha sido creado / modificado correctamente.
messageTemplate.form.remove.message.success = La plantilla seleccionada se ha eliminado.
messageTemplate.form.remove.message.error = La plantilla seleccionada no podía me eliminado.


messageTemplate.delete.confirm.title = Irreversible Mensaje Plantilla Supresión
messageTemplate.delete.confirm.body = ¿Realmente desea eliminar
messageTemplate.delete.confirm.close = Cerrar
messageTemplate.delete.confirm.delete = Eliminar plantilla

##### ##### Suscripciones

# Lista
subscription.list.global.title = Lista de suscripción
subscription.list.table.id = ID
subscription.list.table.title = Título
subscription.list.table.amount = Cuota
subscription.list.table.periodicity = Periodicidad
subscription.list.empty.link = Haga clic aquí para crear una nueva suscripción
subscription.list.empty.text = No hay suscripciones disponibles para ser mostrado.
subscription.list.empty.title = No hay suscripciones disponibles
subscription.list.table.subscribers = Suscriptores
subscription.list.table.installments = Cuotas


# Títulos
subscription.form.global.new.title = Crear una nueva suscripción
subscription.form.global.edit.title = Editar suscripción

# Etiquetas
subscription.form.label.title = Título
subscription.form.label.description = Descripción
subscription.form.label.amount = Importe Cuota
subscription.form.label.periodicity = Periodicidad
subscription.form.label.dueDatePeriod = suscripción próxima fecha de vencimiento
subscription.form.label.subscribers = Suscriptores
subscription.form.label.endDate = Suscripción fecha última cuota

# Ayudantes
subscription.form.helper.title = Por favor, introduzca el título de suscripción
subscription.form.helper.description = Por favor, introduzca la descripción suscripción
subscription.form.helper.amount = Por favor ingrese la cantidad a plazos de suscripción
subscription.form.helper.periodicity = Por favor, introduzca el período de suscripción a plazos
subscription.form.helper.dueDatePeriod = Por favor, introduzca la suscripción próxima fecha de vencimiento
subscription.form.helper.endDate = Por favor, introduzca la suscripción última fecha de entrega

# Placeholder
subscription.form.placeholder.title =
subscription.form.placeholder.description =
subscription.form.placeholder.amount =
subscription.form.placeholder.periodicity =
subscription.form.placeholder.dueDatePeriod =
subscription.form.placeholder.endDate =


# Validación
subscription.form.validation.title = No se dio título.
subscription.form.validation.description = La descripción no se le dio.
subscription.form.validation.amount = se definió Ninguna cantidad a plazos.
subscription.form.validation.periodicity = se introdujo ningún plazo a plazos.
subscription.form.validation.dueDatePeriod = Sin fecha de vencimiento de suscripción se ha introducido.
subscription.form.validation.endDate = Sin suscripción última fecha de entrega fue ingresado.
subscription.form.validation.dueDatePeriod.before = Fecha de vencimiento debe ser mayor que la de la fecha de hoy
subscription.form.validation.endDate.before  = Último tramo debe ser mayor que la de la fecha y la fecha de vencimiento inicial de hoy.

# Mensajes
subscription.form.save.message.notification = La suscripción "{0}" ha sido creado / modificado correctamente.
subscription.form.remove.message.success = La suscripción seleccionada se ha eliminado.
subscription.form.remove.message.error = La suscripción seleccionada no podría haberme retirado.
subscription.form.edit.note = Los cambios realizados en la cantidad y la cuota vencida y periodicidad se reflejarán en cuotas actuales y futuros sólo si no pago se ha registrado todavía.

subscription.mail.subject.update = actualización de suscripción
subscription.mail.body.line1 = Hi there.
subscription.mail.body.line2 = No se ha cambiado a la "{0}" suscripción. La información de facturación de suscripción es una continuación:
subscription.mail.body.line3 = Gracias, tener un día precioso.


subscription.delete.confirm.title = Irreversible suscripción Supresión
subscription.delete.confirm.body1 = ¿Realmente desea eliminar
subscription.delete.confirm.body2 = y toda su información de entrega y pago
subscription.delete.confirm.close = Cerrar
subscription.delete.confirm.delete = Eliminar la suscripción

model.periodicity.unique.title = Un temporizador
model.periodicity.week.title = Semanal
model.periodicity.month.title = Mensual
model.periodicity.bimonth.title = Bimestral
model.periodicity.trimester.title = Trimestral
model.periodicity.semester.title = Semestral
model.periodicity.year.title = anual



##### MailMessage ####

# Títulos
mailMessage.form.send.title = redactar mensaje

# Etiquetas
mailMessage.form.label.subject = Asunto
mailMessage.form.label.body = Cuerpo
mailMessage.form.label.recipients = Destinatarios

# Ayudantes
mailMessage.form.helper.subject = Por favor, introduzca el asunto del mensaje
mailMessage.form.helper.body = Por favor, introduzca el cuerpo del mensaje
mailMessage.form.helper.recipients = Por favor, seleccione los destinatarios de mensajes


# Placeholder
mailMessage.form.placeholder.subject =
mailMessage.form.placeholder.body =
mailMessage.form.placeholder.recipients =

# Validación
mailMessage.form.validation.subject = No se dio ninguna materia.
mailMessage.form.validation.body = No se dio cuerpo.
mailMessage.form.validation.recipients = No se seleccionaron los beneficiarios.

# Mensajes
mailMessage.form.save.message.notification = El mensaje ha sido enviado
mailMessage.form.save.message.notification.message = El mensaje ha sido enviado a todos los destinatarios seleccionados.
mailMessage.form.global.new.title = Enviar nuevo mensaje

##### Pago ####

payment.form.instructions.installment = Por favor, seleccione una cuota activa e introduzca la cantidad de su pago.

payment.form.label.installment = Cuota a pagar
payment.form.helper.installment = Seleccione la cuota para realizar el pago a.
payment.form.label.amount = cantidad a pagar
payment.form.helper.amount = Introduzca la cantidad que desea paga para entrega seleccionado

payment.form.placeholder.amount =
payment.form.placeholder.installment =

payment.form.validation.memberInstallmentToken = Seleccione una cuota a pagar
payment.form.validation.amount = Proporcionar la cantidad a pagar
payment.form.validation.excess = La cantidad de su pago es superior a la cantidad debida de {0}

payment.form.save.message.notification = Su pago ha sido registrada.

payment.show.chart.subscription = Evolución pago acumulativo

##### Iniciar sesión ####

login.form.label.email = Email
login.form.placeholder.email =
login.form.helper.email = Por favor, introduzca su correo electrónico
login.form.validation.email = Proporcione una dirección de correo electrónico válida

login.form.label.password = Contraseña
login.form.placeholder.password =
login.form.helper.password = Por favor, Entre su contraseña
login.form.validation.password = Introduzca su contraseña

login.form.password.link = Olvidé mi contraseña
login.form.header.title = Ingresa para iniciar su sesión
login.form.auth.error = credenciales no válidas
user.general.logout.label = Salir
user.general.profile.edit = Editar perfil
user.general.profile.show = Perfil
user.login.passwordRecovery.title = Password Recovery
login.form.submit.label = Entrar
login.form.recovery.label = Recuperar
user.login.global.title = Ingresar Registro

login.form.passwordRecovery.title = Introduce tu dirección de correo electrónico para recuperar su contraseña
login.form.login.link = Ingresa
login.form.recovery.error = cuenta de correo electrónico válida.

login.mail.subject.recovery = Password Recovery
recovery.mail.body.line1 = Hi there.
recovery.mail.body.line2 = Su contraseña ha sido reinicializado.
recovery.mail.body.line3a = Usted nombre de usuario es
recovery.mail.body.line3b = y su nueva contraseña es
recovery.mail.body.line4 = Por favor, recuerde que es posible que desee cambiar su nombre de usuario / dirección de correo electrónico a la contraseña en cualquier momento desde que el perfil de usuario.
recovery.mail.body.line5 = Gracias, tener un día precioso.

##### MemberInstallment ####

memberInstallment.mail.body.line1 = Aviso Cuota
memberInstallment.mail.body.line2 = Una nueva entrega se ha lanzado para el '' {0} '' suscripción.
memberInstallment.mail.body.line3 = El monto total a pagar por esta entrega es de {0}. Por favor, haga clic en el siguiente enlace para proceder a su pago.
memberInstallment.mail.body.line4 = Haga clic aquí para pagar ahora.
memberInstallment.mail.body.line5 = Esta nueva entrega se debe a la {0}.
memberInstallment.mail.body.line6 = Gracias, tiene un día precioso.

##### Pago Pública ####

paymentPublic.form.header.title = Por favor ingrese la cantidad que con el que pagar a la entrega seleccionado.
paymentPublic.form.payed.title = Esta entrega de suscripción ya ha sido pagado en su totalidad.
paymentPublic.form.amount.due = Importe a pagar
paymentPublic.message.pay.success = Su pago se ha registrado correctamente.
paymentPublic.message.pay.fail = Su pago no se pudo procesar.
paymentPublic.message.pay.thankyou=  Gracias
paymentPublic.message.pay.sorry = Lo siento
paymentPublic.pay.paypal.title = pago de PayPal
paymentPublic.pay.complete.title = Pago completado

##### Estadística ####

statistic.global.title.dashboard = Dashboard
statistic.dashboard.payments.title = Últimos Pagos
statistic.dashboard.chart.title = Total Colecciones
statistic.dashboard.installments.title = Cuotas actuales
