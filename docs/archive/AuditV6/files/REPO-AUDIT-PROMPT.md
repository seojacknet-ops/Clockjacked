# Repo Audit — Claude Code Prompt

Paste this into Claude Code:

```
Read REPO-AUDIT.md in the project root.

Perform a full repository audit of the ClockJacked project. The repo has evolved through iteration folders _v1, _v2, _v3, and _v4 and needs a comprehensive health check.

Execute all 6 phases from REPO-AUDIT.md in order:
1. Discovery & Inventory — map every file, understand each version folder
2. Health Check — verify build, dependencies, code quality, feature status, widget status, assets
3. Inconsistency Detection — find conflicts between versions, docs vs code, naming issues
4. File Cleanup — remove dead files, duplicates, temp artifacts
5. Architecture Restructure — reorganize into the clean folder structure defined in Phase 5A
6. Final Audit Report — produce AUDIT-REPORT.md summarizing everything

Rules:
- Commit current state first before making changes
- Verify the build after every phase
- Archive version folders to docs/archive/ — DO NOT delete them
- Log every file move/deletion in CLEANUP-LOG.md
- Update CLAUDE.md and TODO.md to reflect the new structure
- DO NOT change app logic or features — structural cleanup only

Start with Phase 1. Go.
```
