import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { QsTrabalhoDisciplinaDisciplinaModule } from './disciplina/disciplina.module';
import { QsTrabalhoDisciplinaTimeSlotModule } from './time-slot/time-slot.module';
import { QsTrabalhoDisciplinaProgramaModule } from './programa/programa.module';
import { QsTrabalhoDisciplinaPessoaModule } from './pessoa/pessoa.module';
import { QsTrabalhoDisciplinaProfessorModule } from './professor/professor.module';
import { QsTrabalhoDisciplinaAlunoModule } from './aluno/aluno.module';
import { QsTrabalhoDisciplinaLocalModule } from './local/local.module';
import { QsTrabalhoDisciplinaTurmaModule } from './turma/turma.module';
import { QsTrabalhoDisciplinaInscricaoModule } from './inscricao/inscricao.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        QsTrabalhoDisciplinaDisciplinaModule,
        QsTrabalhoDisciplinaTimeSlotModule,
        QsTrabalhoDisciplinaProgramaModule,
        QsTrabalhoDisciplinaPessoaModule,
        QsTrabalhoDisciplinaProfessorModule,
        QsTrabalhoDisciplinaAlunoModule,
        QsTrabalhoDisciplinaLocalModule,
        QsTrabalhoDisciplinaTurmaModule,
        QsTrabalhoDisciplinaInscricaoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QsTrabalhoDisciplinaEntityModule {}
